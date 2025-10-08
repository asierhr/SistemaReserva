package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.domain.dto.SeatDTO;
import com.asier.SistemaReservas.domain.enums.SeatClass;
import com.asier.SistemaReservas.servicies.SeatService;
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
