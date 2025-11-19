package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.AirportDTO;
import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.entities.AirportEntity;

import java.util.List;
import java.util.Map;

public interface AirportService {
    AirportDTO createAirport(AirportDTO airport);
    AirportEntity getAirport(Long id);
    AirportDTO getAirportByName(String name);
    AirportEntity getAirportByLocationCity(String city);
    Map<String, List<FlightDTO>> getTodayFlights();
}
