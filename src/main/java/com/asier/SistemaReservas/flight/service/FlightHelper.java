package com.asier.SistemaReservas.flight.service;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;

import java.util.List;

public interface FlightHelper {
    List<SeatEntity> getAvailableSeatsForFlight(Long flightId);
    boolean flightExists(Long flightId);
    AirportEntity getAirport(Long id);
    AirportEntity getAirportByLocationCity(String city);
}
