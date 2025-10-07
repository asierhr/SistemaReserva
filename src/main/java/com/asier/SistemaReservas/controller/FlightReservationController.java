package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.servicies.FlightReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightReservationController {
    private final FlightReservationService flightReservationService;

    @PostMapping(path = "/flight/{id}/seats/reservation")
    public FlightReservationDTO createFlightReservation(@PathVariable Long id, @RequestBody List<Long> seatsIds){
        return flightReservationService.createFlightReservation(id, seatsIds);
    }
}
