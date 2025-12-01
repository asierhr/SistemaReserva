package com.asier.SistemaReservas.reservation.event.records;

import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;

public record ReservationCreatedEvent(
        FlightReservationEntity flightReservation) {
}
