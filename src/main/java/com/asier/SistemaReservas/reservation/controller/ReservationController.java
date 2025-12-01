package com.asier.SistemaReservas.reservation.controller;

import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.service.ReservationService;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.domain.records.CheckInResponse;
import com.asier.SistemaReservas.system.QR.domain.records.QRValidationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    @PostMapping(path = "/reservations/checkIn/validate")
    public ResponseEntity<CheckInResponse> validateQR(@RequestBody QRValidationRequest request){
        try{
            JsonNode qrData = objectMapper.readTree(request.qrContent());
            String type = qrData.get("type").asText();
            Long reservationId = qrData.get("reservationId").asLong();
            String mail = qrData.get("email").asText();

            ReservationEntity reservation = reservationService.getReservation(reservationId);
            if(!reservation.getUser().getMail().equals(mail)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new CheckInResponse(false, "QR does not match reservation owner"));
            }

            if(reservation.getBookingStatus() == BookingStatus.PAID){
                return ResponseEntity.ok(
                        new CheckInResponse(false, "Reservation not confirmed. Status: " + reservation.getBookingStatus())
                );
            }

            if (reservation.getCheckedIn() != null && reservation.getCheckedIn()) {
                return ResponseEntity.ok(
                        new CheckInResponse(true, "Already checked in at " + reservation.getCheckInTime())
                );
            }

            reservation.setCheckedIn(true);
            reservation.setCheckInTime(LocalDateTime.now());
            reservationService.updateReservation(reservation);

            return ResponseEntity.ok(
                    new CheckInResponse(true, "Check-in successful!")
            );
        } catch (JsonProcessingException e) {
            log.error("❌ Invalid QR format", e);
            return ResponseEntity.badRequest()
                    .body(new CheckInResponse(false, "Invalid QR code format"));
        } catch (Exception e) {
            log.error("❌ Error during check-in", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CheckInResponse(false, "Internal error"));
        }
    }



}
