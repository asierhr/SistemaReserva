package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.domain.enums.SeatClass;
import com.asier.SistemaReservas.domain.records.FlightReservationRequest;
import com.asier.SistemaReservas.domain.records.FlightSearch;
import com.asier.SistemaReservas.servicies.FlightReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightReservationController {
    private final FlightReservationService flightReservationService;

    @PostMapping(path = "/flights/{id}/seats/reservation")
    public FlightReservationDTO createFlightReservation(@PathVariable Long id, @RequestBody FlightReservationRequest request){
        return flightReservationService.createFlightReservation(id, request);
    }

    @GetMapping(path = "/users/flights/reservations")
    public List<FlightReservationDTO> getAllFlightsReservations(){
        return flightReservationService.getAllFlightReservations();
    }
}
