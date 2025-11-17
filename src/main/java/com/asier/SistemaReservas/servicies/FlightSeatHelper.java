package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.entities.SeatEntity;

import java.util.List;

public interface FlightSeatHelper {
    List<SeatEntity> getAvailableSeatsForFlight(Long flightId);
    boolean flightExists(Long flightId);
}
