package com.asier.SistemaReservas.flight.controller;

import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightSummaryDTO;
import com.asier.SistemaReservas.flight.domain.records.FlightSearch;
import com.asier.SistemaReservas.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/flights/search")
    public List<FlightPairDTO> getFlightsBySearch(@RequestBody FlightSearch flightSearch){
        return flightService.getFlightsBySearch(flightSearch);
    }

    @GetMapping(path = "/flights/origins")
    public List<String> getAllOrigins(){
        return flightService.getAllOrigins();
    }

    @GetMapping(path = "/flights/destinations")
    public List<String> getAllDestinations(){
        return flightService.getAllDestinations();
    }
}
