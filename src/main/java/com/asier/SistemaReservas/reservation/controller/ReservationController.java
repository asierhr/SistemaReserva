package com.asier.SistemaReservas.reservation.controller;

import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.exceptions.ReservationNotFoundException;
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
        try {
            CheckInResponse response = reservationService.validateQR(request);

            if (!response.success()) {
                return ResponseEntity.status(
                        response.message().contains("does not match") ? HttpStatus.FORBIDDEN : HttpStatus.BAD_REQUEST
                ).body(response);
            }

            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e) {
            log.error("❌ Invalid QR format", e);
            return ResponseEntity.badRequest()
                    .body(new CheckInResponse(false, "Invalid QR code format"));
        } catch (ReservationNotFoundException e) {
            log.error("❌ Reservation not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CheckInResponse(false, "Reservation not found"));
        } catch (Exception e) {
            log.error("❌ Error during check-in", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CheckInResponse(false, "Internal error"));
        }
    }



}
