package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.entities.FlightEntity;

public interface FlightService {
    FlightDTO createFlight(FlightDTO flight);
    FlightDTO getFlight(Long id);
    FlightSummaryDTO getSummaryFlight(Long id);
    boolean existsById(Long id);
    FlightEntity getFlightEntity(Long id);
}
