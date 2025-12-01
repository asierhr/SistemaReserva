package com.asier.SistemaReservas.reservation.domain.enums;

public enum BookingStatus {
    PENDING_PAYMENT,
    PAID,
    CONFIRMED,
    EXPIRED,
    REFUNDED,
    NOT_SHOWN,
    PAYMENT_FAILED
}