package com.asier.SistemaReservas.flight.controller;

import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightSummaryDTO;
import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.search.flightSearch.domain.entity.FlightSearchEntity;
import com.asier.SistemaReservas.flight.service.FlightService;
import com.asier.SistemaReservas.system.IpLocation.service.IpGeolocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final IpGeolocationService ipGeolocationService;

    @PostMapping(path = "/flights")
    public FlightDTO createFlight(@RequestBody FlightDTO flight){
        return flightService.createFlight(flight);
    }

    @GetMapping(path = "/flights/{id}")
    public ResponseEntity<FlightSummaryDTO> getFlight(@PathVariable Long id){
        FlightSummaryDTO flight = flightService.getSummaryFlight(id);
        return ResponseEntity.ok(flight);
    }

    @PostMapping(path = "/flights/search")
    public List<FlightPairDTO> getFlightsBySearch(@RequestBody FlightSearchDTO flightSearch, HttpServletRequest request){
        String ipAddress = ipGeolocationService.extractIpFromRequest(request);
        return flightService.getFlightsBySearch(flightSearch, ipAddress);
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
