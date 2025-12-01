package com.asier.SistemaReservas.reservation.hotelReservation.controller;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.records.ReservationHotelRequest;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelReservationController {
    private final HotelReservationService hotelReservationService;

    @PostMapping(path = "/hotels/{id}/rooms/reservation")
    public HotelReservationDTO createReservation(@PathVariable Long id, @RequestBody ReservationHotelRequest request){
        return hotelReservationService.createReservation(id, request);
    }

    @GetMapping(path = "/users/hotels/reservations")
    public List<HotelReservationDTO> getAllHotelReservations(){
        return hotelReservationService.getUserReservations();
    }
}
