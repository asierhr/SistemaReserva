package com.asier.SistemaReservas.seats.controller;

import com.asier.SistemaReservas.seats.domain.enums.SeatClass;
import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import com.asier.SistemaReservas.seats.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping(path = "/flights/{id}/seats")
    public Map<SeatClass,List<SeatDTO>> getSeatsFromFlight(@PathVariable Long id){
        return seatService.getSeatsFromFlight(id);
    }


}
