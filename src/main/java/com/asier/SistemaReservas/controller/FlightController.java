package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightPairDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.records.FlightSearch;
import com.asier.SistemaReservas.servicies.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
