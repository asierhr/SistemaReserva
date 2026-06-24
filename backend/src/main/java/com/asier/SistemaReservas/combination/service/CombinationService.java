package com.asier.SistemaReservas.combination.service;

import com.asier.SistemaReservas.combination.domain.dto.CombinationDTO;
import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;

public interface CombinationService {
    CombinationDTO getCombinationBySearch(FlightSearchDTO flightSearchDTO, String ipAddress);
}
