package com.asier.SistemaReservas.reservation.flightReservation.domain.records;

import com.asier.SistemaReservas.flight.domain.records.FlightSearch;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;

public record FlightReservationRequest(
        FlightSearch flightSearch,
        SeatClass seatClass
) {}