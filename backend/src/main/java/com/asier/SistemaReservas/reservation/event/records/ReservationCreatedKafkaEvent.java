package com.asier.SistemaReservas.reservation.event.records;

import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationCreatedKafkaEvent(
        Long reservationId,
        Long userId,
        BigDecimal totalPrice,
        LocalDateTime reservationDate,
        BookingStatus bookingStatus
) {}
