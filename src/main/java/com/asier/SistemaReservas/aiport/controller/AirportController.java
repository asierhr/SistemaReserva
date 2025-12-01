package com.asier.SistemaReservas.aiport.controller;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.aiport.service.AirportService;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PostMapping(path = "/airports")
    public AirportDTO createAirport(@RequestBody AirportDTO airport){
        return airportService.createAirport(airport);
    }

    @GetMapping(path = "/airports/flights")
    public Map<String, List<FlightDTO>> getTodayFlights(){
        return airportService.getTodayFlights();
    }
}
