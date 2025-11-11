package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.HotelHistoryDTO;
import com.asier.SistemaReservas.servicies.HotelHistoryService;
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
