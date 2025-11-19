package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightPairDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.entities.FlightEntity;
import com.asier.SistemaReservas.domain.records.FlightSearch;

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
