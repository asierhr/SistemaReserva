package com.asier.SistemaReservas.domain.records;

import com.asier.SistemaReservas.domain.enums.SeatClass;

public record FlightReservationRequest(
        FlightSearch flightSearch,
        SeatClass seatClass
) {}