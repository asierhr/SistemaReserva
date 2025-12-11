package com.asier.SistemaReservas.reservation.flightReservation.domain.records;

import com.asier.SistemaReservas.search.flightSearch.domain.entity.FlightSearchEntity;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;

public record FlightReservationRequest(
        FlightSearchEntity flightSearch,
        SeatClass seatClass
) {}