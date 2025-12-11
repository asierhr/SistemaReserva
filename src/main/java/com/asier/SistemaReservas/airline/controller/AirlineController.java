package com.asier.SistemaReservas.airline.controller;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AirlineController {
    private final AirlineService airlineService;

    @PostMapping(path = "/airline")
    public AirlineDTO createAirline(@RequestBody AirlineDTO airlineDTO){
        return airlineService.createAirline(airlineDTO);
    }

    @GetMapping(path = "/airline/{id}/employees")
    public List<AirlineEmployeeInfoDTO> getEmployeesFromAirline(@PathVariable Long id){
        return airlineService.getEmployeesFromAirline(id);
    }
}
