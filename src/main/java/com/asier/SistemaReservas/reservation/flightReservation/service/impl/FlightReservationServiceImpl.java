package com.asier.SistemaReservas.reservation.flightReservation.service.impl;

import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.flight.service.FlightService;
import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.service.LoyaltyBenefitsService;
import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import com.asier.SistemaReservas.reservation.flightReservation.domain.DTO.FlightReservationDTO;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.flightReservation.repository.FlightReservationRepository;
import com.asier.SistemaReservas.reservation.flightReservation.service.FlightReservationService;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.seats.service.SeatService;
import com.asier.SistemaReservas.system.Locks.DistributedLockService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.flightReservation.domain.records.FlightReservationRequest;
import com.asier.SistemaReservas.reservation.event.records.ReservationCreatedEvent;
import com.asier.SistemaReservas.reservation.flightReservation.mapper.FlightReservationMapper;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightReservationServiceImpl implements FlightReservationService {
    private final FlightReservationRepository flightReservationRepository;
    private final FlightService flightService;
    private final SeatService seatService;
    private final FlightReservationMapper flightReservationMapper;
    private final UserService userService;
    private final DistributedLockService distributedLockService;
    private final ApplicationEventPublisher eventPublisher;
    private final LoyaltyBenefitsService loyaltyBenefitsService;
    private final LoyaltyService loyaltyService;

    private static final int MAX_RETRIES = 3;

    private BigDecimal validateSeatsAndCost(List<SeatEntity> seats, Long flightId) {
        if (seats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No seats provided");
        }
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (SeatEntity seat : seats) {
            if (!flightId.equals(seat.getFlight().getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Seat " + seat.getId() + " does not belong to flight " + flightId);
            }
            if (!seat.isAvailable()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Seat " + seat.getId() + " is already reserved");
            }
            totalPrice = totalPrice.add(seat.getCostPerSeat());
        }
        return totalPrice;
    }

    @Override
    public FlightReservationDTO createFlightReservation(Long id, FlightReservationRequest request) {
        if (!flightService.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");

        UserEntity currentUser = userService.getUserEntity();



        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            List<SeatEntity> candidates = seatService.getAvailableSeats(id).stream()
                    .filter(seat -> seat.getSeatClass() == request.seatClass())
                    .toList();

            Collections.shuffle(candidates, ThreadLocalRandom.current());

            List<SeatEntity> availableSeats = candidates.stream()
                    .limit(request.flightSearch().passengers())
                    .toList();

            if (availableSeats.size() < request.flightSearch().passengers()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough seats available");
            }


            List<String> seatLockKeys = availableSeats.stream()
                    .map(seat -> "seat:" + seat.getId())
                    .toList();

            try {
                FlightReservationDTO result = distributedLockService.executeWithMultiLock(seatLockKeys, () -> {
                    return processReservationInTransaction(id, availableSeats, request, currentUser);
                });

                FlightReservationEntity savedEntity = flightReservationRepository.findById(result.getId())
                        .orElseThrow();

                eventPublisher.publishEvent(new ReservationCreatedEvent(savedEntity));

                return result;


            } catch (ResponseStatusException e) {
                if (e.getStatusCode() == HttpStatus.CONFLICT && attempt < MAX_RETRIES - 1) {
                    log.warn("Attempt {} failed, retrying...", attempt + 1);
                    continue;
                }
                throw e;
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not complete reservation");
    }

    @Transactional
    private FlightReservationDTO processReservationInTransaction(
            Long flightId,
            List<SeatEntity> seats,
            FlightReservationRequest request,
            UserEntity user) {

        List<SeatEntity> freshSeats = seats.stream()
                .map(seat -> seatService.getSeatFromId(seat.getId()))
                .toList();

        BigDecimal price = validateSeatsAndCost(freshSeats, flightId);
        LoyaltyTierEntity tier = loyaltyService.getLoyaltyByUser(userService.getUserEntity().getId()).getLoyaltyTier();
        FlightEntity flight = flightService.getFlightEntity(flightId);

        FlightReservationEntity reservation = FlightReservationEntity.builder()
                .reservationDate(LocalDateTime.now())
                .bookingStatus(BookingStatus.PENDING_PAYMENT)
                .totalPrice(price)
                .totalPriceAfterDiscount(loyaltyBenefitsService.applyBenefits(user,price).finalPrice())
                .user(user)
                .flight(flight)
                .seat(freshSeats)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .cancellationDeadline(loyaltyBenefitsService.getCancellationDeadline(tier, LocalDateTime.of(flight.getFlightDay(),flight.getDepartureTime())))
                .build();


        for (SeatEntity seat : reservation.getSeat()) {
            seat.setReservation(reservation);
            seat.setAvailable(false);
        }

        FlightReservationEntity saved = flightReservationRepository.save(reservation);

        return flightReservationMapper.toDTO(saved);
    }

    @Override
    public List<FlightReservationEntity> getFlightReservationsExpired() {
        return flightReservationRepository.findExpiredPendingReservations(LocalDateTime.now());
    }

    @Override
    public void updateFlightsReservations(List<FlightReservationEntity> flightReservations) {
        flightReservationRepository.saveAll(flightReservations);
    }

    @Override
    public List<FlightReservationDTO> getAllFlightReservations() {
        UserEntity user = userService.getUserEntity();
        return flightReservationMapper.toDTOList(flightReservationRepository.findAllByUserId(user.getId()));
    }

    @Override
    public FlightReservationEntity getFlightById(Long id) {
        return flightReservationRepository.findById(id).orElseThrow();
    }

    @Override
    public void updateFlightReservation(FlightReservationEntity flightReservation) {
        flightReservationRepository.save(flightReservation);
    }

    @Override
    public List<FlightReservationEntity> getAllCheckInFalseAndFlightDone() {
        return flightReservationRepository.findAllByCheckInFalseAndFlightDone(LocalDate.now(), LocalDateTime.now());
    }
}
