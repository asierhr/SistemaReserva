package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.entities.AirportEntity;
import com.asier.SistemaReservas.domain.entities.SeatEntity;

import java.util.List;

public interface FlightHelper {
    List<SeatEntity> getAvailableSeatsForFlight(Long flightId);
    boolean flightExists(Long flightId);
    AirportEntity getAirport(Long id);
    AirportEntity getAirportByLocationCity(String city);
}
