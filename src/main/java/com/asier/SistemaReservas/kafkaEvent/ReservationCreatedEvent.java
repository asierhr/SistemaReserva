package com.asier.SistemaReservas.kafkaEvent;

import com.asier.SistemaReservas.domain.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservationCreatedEvent(Long reservationId, Long userId, BigDecimal totalPrice, LocalDateTime reservationDate, BookingStatus bookingStatus) {
}
