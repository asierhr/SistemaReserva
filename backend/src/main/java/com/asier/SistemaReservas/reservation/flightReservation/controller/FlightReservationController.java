package com.asier.SistemaReservas.reservation.flightReservation.controller;

import com.asier.SistemaReservas.reservation.flightReservation.domain.DTO.FlightReservationDTO;
import com.asier.SistemaReservas.reservation.flightReservation.domain.records.FlightReservationRequest;
import com.asier.SistemaReservas.reservation.flightReservation.service.FlightReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightReservationController {
    private final FlightReservationService flightReservationService;

    @PostMapping(path = "/flights-reservation/{id}/seats")
    public FlightReservationDTO createFlightReservation(@PathVariable Long id, @RequestBody FlightReservationRequest request){
        return flightReservationService.createFlightReservation(id, request);
    }

    @GetMapping(path = "/users/flights/reservations")
    public List<FlightReservationDTO> getAllFlightsReservations(){
        return flightReservationService.getAllFlightReservations();
    }
}
