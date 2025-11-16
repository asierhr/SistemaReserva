package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.dto.SeatDTO;
import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.domain.enums.BookingStatus;
import com.asier.SistemaReservas.domain.records.FlightSearch;
import com.asier.SistemaReservas.mapper.FlightReservationMapper;
import com.asier.SistemaReservas.repositories.FlightReservationRepository;
import com.asier.SistemaReservas.servicies.FlightReservationService;
import com.asier.SistemaReservas.servicies.FlightService;
import com.asier.SistemaReservas.servicies.SeatService;
import com.asier.SistemaReservas.servicies.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightReservationServiceImpl implements FlightReservationService {
    private final FlightReservationRepository flightReservationRepository;
    private final FlightService flightService;
    private final SeatService seatService;
    private final FlightReservationMapper flightReservationMapper;
    private final UserService userService;

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
    @Transactional
    public FlightReservationDTO createFlightReservation(Long id, FlightSearch flightSearch) {
        if(!flightService.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found");
        List<SeatEntity> availableSeats = seatService.getAvailableSeats(id);
        if (availableSeats.size() < flightSearch.passengers()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Not enough seats available. Required: %d, Available: %d",
                            flightSearch.passengers(), availableSeats.size()));
        }
        List<SeatEntity> seats = availableSeats.subList(0, flightSearch.passengers());
        FlightReservationEntity flightReservationEntity = FlightReservationEntity.builder()
                .reservationDate(LocalDateTime.now())
                .bookingStatus(BookingStatus.PENDING_PAYMENT)
                .totalPrice(validateSeatsAndCost(seats, id))
                .user(userService.getUserEntity())
                .flight(flightService.getFlightEntity(id))
                .seat(seats)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        for(SeatEntity seat: flightReservationEntity.getSeat()){
            seat.setReservation(flightReservationEntity);
            seat.setAvailable(false);
        }
        FlightReservationEntity savedFlightReservation = flightReservationRepository.save(flightReservationEntity);
        return flightReservationMapper.toDTO(savedFlightReservation);
    }

    @Override
    public List<FlightReservationEntity> getFlightReservationsExpired() {
        return flightReservationRepository.findExpiredPendingReservations(LocalDateTime.now());
    }

    @Override
    public void updateFlightsReservations(List<FlightReservationEntity> flightReservations) {
        flightReservationRepository.saveAll(flightReservations);
    }
}
