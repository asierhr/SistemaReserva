package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.AirportDTO;
import com.asier.SistemaReservas.servicies.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PostMapping(path = "/airports")
    public AirportDTO createAirport(@RequestBody AirportDTO airport){
        return airportService.createAirport(airport);
    }
}
