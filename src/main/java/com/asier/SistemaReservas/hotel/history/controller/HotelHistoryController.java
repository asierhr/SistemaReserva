package com.asier.SistemaReservas.hotel.history.controller;

import com.asier.SistemaReservas.hotel.history.domain.DTO.HotelHistoryDTO;
import com.asier.SistemaReservas.hotel.history.service.HotelHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelHistoryController {
    private final HotelHistoryService hotelHistoryService;

    @GetMapping(path = "/users/hotels/history")
    public HotelHistoryDTO getHotelHistory(){
        return hotelHistoryService.getHotelHistory();
    }
}
