package com.asier.SistemaReservas.aiport.service;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;

import java.util.List;
import java.util.Map;

public interface AirportService {
    AirportDTO createAirport(AirportDTO airport);
    AirportEntity getAirport(Long id);
    AirportDTO getAirportByName(String name);
    AirportEntity getAirportByLocationCity(String city);
    Map<String, List<FlightDTO>> getTodayFlights();
}
