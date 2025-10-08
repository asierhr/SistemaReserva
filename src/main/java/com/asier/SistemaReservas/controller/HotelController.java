package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.HotelDTO;
import com.asier.SistemaReservas.domain.dto.HotelSummaryDTO;
import com.asier.SistemaReservas.servicies.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping(path = "/hotels")
    public HotelDTO createHotel(@RequestBody HotelDTO hotelDTO){
        return hotelService.createHotel(hotelDTO);
    }

    @GetMapping(path = "/hotels/{id}")
    public HotelSummaryDTO getHotel(@PathVariable Long id){
        return hotelService.getHotel(id);
    }
}
