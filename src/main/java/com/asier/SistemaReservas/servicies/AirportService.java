package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.AirportDTO;
import com.asier.SistemaReservas.domain.entities.AirportEntity;

public interface AirportService {
    AirportDTO createAirport(AirportDTO airport);
    AirportEntity getAirport(Long id);
    AirportDTO getAirportByName(String name);
    AirportEntity getAirportByLocationCity(String city);
}
