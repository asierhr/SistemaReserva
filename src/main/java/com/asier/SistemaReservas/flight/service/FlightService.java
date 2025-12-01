package com.asier.SistemaReservas.flight.service;

import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightSummaryDTO;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.flight.domain.records.FlightSearch;

import java.util.List;

public interface FlightService {
    FlightDTO createFlight(FlightDTO flight);
    FlightDTO getFlight(Long id);
    FlightSummaryDTO getSummaryFlight(Long id);
    boolean existsById(Long id);
    FlightEntity getFlightEntity(Long id);
    List<FlightPairDTO> getFlightsBySearch(FlightSearch flightSearch);
    List<String> getAllOrigins();
    List<String> getAllDestinations();
    List<FlightDTO> transformListEntity(List<FlightEntity> flight);
}
