package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.servicies.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping(path = "/flights")
    public FlightDTO createFlight(@RequestBody FlightDTO flight){
        return flightService.createFlight(flight);
    }

    @GetMapping(path = "/flights/{id}")
    public ResponseEntity<FlightSummaryDTO> getFlight(@PathVariable Long id){
        FlightSummaryDTO flight = flightService.getSummaryFlight(id);
        return ResponseEntity.ok(flight);
    }
}
