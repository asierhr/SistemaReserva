package com.asier.SistemaReservas.reservation.event.records;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;

public record ReservationCreatedEvent(
        ReservationEntity reservation) {
}
