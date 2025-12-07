package com.asier.SistemaReservas.reservation.service.impl;

import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.domain.records.CheckInResponse;
import com.asier.SistemaReservas.reservation.exceptions.ReservationNotFoundException;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.repository.ReservationRepository;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.service.ReservationService;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.system.QR.domain.records.QRValidationRequest;
import com.asier.SistemaReservas.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    public ReservationEntity getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    @Override
    public void updateReservation(ReservationEntity reservation) {
        reservationRepository.save(reservation);
    }

    @Override
    public void refundReservation(ReservationEntity reservation) {
        if(reservation instanceof FlightReservationEntity flightReservation){
            List<SeatEntity> seats = flightReservation.getSeat();
            for(SeatEntity seat: seats) {
                seat.setAvailable(true);
            }
        }else if(reservation instanceof HotelReservationEntity hotelReservation){
            hotelReservation.setCheckIn(null);
            hotelReservation.setCheckOut(null);
        }
        updateReservation(reservation);
    }

    @Override
    public CheckInResponse validateQR(QRValidationRequest request) throws JsonProcessingException {
        JsonNode qrData = objectMapper.readTree(request.qrContent());
        String type = qrData.get("type").asText();
        Long reservationId = qrData.get("reservationId").asLong();
        String email = qrData.get("email").asText();


        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));


        if (!reservation.getUser().getMail().equals(email)) {
            return new CheckInResponse(false, "QR does not match reservation owner");
        }


        if (reservation.getBookingStatus() != BookingStatus.PAID) {
            return new CheckInResponse(false,
                    "Reservation not confirmed. Status: " + reservation.getBookingStatus());
        }


        if (Boolean.TRUE.equals(reservation.getCheckedIn())) {
            return new CheckInResponse(true,
                    "Already checked in at " + reservation.getCheckInTime());
        }


        reservation.setCheckedIn(true);
        reservation.setCheckInTime(LocalDateTime.now());
        reservationRepository.save(reservation);

        log.info("✅ Check-in successful for reservation: {}", reservationId);

        return new CheckInResponse(true, "Check-in successful!");
    }
}
