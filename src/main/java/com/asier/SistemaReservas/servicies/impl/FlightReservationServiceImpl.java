package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.domain.enums.BookingStatus;
import com.asier.SistemaReservas.domain.records.FlightReservationRequest;
import com.asier.SistemaReservas.kafkaEvent.ReservationEventProducer;
import com.asier.SistemaReservas.mapper.FlightReservationMapper;
import com.asier.SistemaReservas.repositories.FlightReservationRepository;
import com.asier.SistemaReservas.servicies.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightReservationServiceImpl implements FlightReservationService {
    private final FlightReservationRepository flightReservationRepository;
    private final FlightService flightService;
    private final SeatService seatService;
    private final FlightReservationMapper flightReservationMapper;
    private final UserService userService;
    private final ReservationEventProducer reservationEventProducer;
    private final DistributedLockService distributedLockService;

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

    @Override  // ← Quitar @Transactional de aquí
    public FlightReservationDTO createFlightReservation(Long id, FlightReservationRequest request) {
        if (!flightService.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");

        UserEntity currentUser = userService.getUserEntity();

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            List<SeatEntity> availableSeats = seatService.getAvailableSeats(id).stream()
                    .filter(seat -> seat.getSeatClass() == request.seatClass())
                    .sorted(Comparator.comparing(SeatEntity::getId))
                    .limit(request.flightSearch().passengers())
                    .toList();

            if (availableSeats.size() < request.flightSearch().passengers()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough seats available");
            }

            List<String> seatLockKeys = availableSeats.stream()
                    .map(seat -> "seat:" + seat.getId())
                    .toList();

            try {
                return distributedLockService.executeWithMultiLock(seatLockKeys, () -> {
                    return processReservationInTransaction(id, availableSeats, request, currentUser);
                });
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

        for (SeatEntity seat : freshSeats) {
            if (!seat.isAvailable()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Seat " + seat.getSeatNumber() + " was just taken");
            }
        }

        FlightReservationEntity reservation = FlightReservationEntity.builder()
                .reservationDate(LocalDateTime.now())
                .bookingStatus(BookingStatus.PENDING_PAYMENT)
                .totalPrice(validateSeatsAndCost(freshSeats, flightId))
                .user(user)
                .flight(flightService.getFlightEntity(flightId))
                .seat(freshSeats)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();

        for (SeatEntity seat : reservation.getSeat()) {
            seat.setReservation(reservation);
            seat.setAvailable(false);
        }

        FlightReservationEntity saved = flightReservationRepository.save(reservation);

        try {
            reservationEventProducer.sendReservationCreatedEvent(saved);
        } catch (JsonProcessingException e) {
            log.error("Failed to send reservation event", e);
        }

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
}
