package com.asier.SistemaReservas.search.flightSearch.service;

import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;

import java.util.List;

public interface FlightSearchService {
    void saveSearch(FlightSearchDTO flightSearch, String ipAddress);
    List<FlightSearchDTO> getSearchByUser();
}
