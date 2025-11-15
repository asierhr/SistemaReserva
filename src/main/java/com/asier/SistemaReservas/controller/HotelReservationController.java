package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;
import com.asier.SistemaReservas.domain.records.ReservationHotelRequest;
import com.asier.SistemaReservas.servicies.HotelReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelReservationController {
    private final HotelReservationService hotelReservationService;

    @PostMapping(path = "/hotels/{id}/rooms/reservation")
    public HotelReservationDTO createReservation(@PathVariable Long id, @RequestBody ReservationHotelRequest request){
        return hotelReservationService.createReservation(id, request.roomIds(), request.checkIn(), request.checkOut());
    }
}
