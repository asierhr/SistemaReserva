package com.asier.SistemaReservas.reservation.domain.records;

public record CheckInResponse(
        boolean success,
        String message
) {
}
