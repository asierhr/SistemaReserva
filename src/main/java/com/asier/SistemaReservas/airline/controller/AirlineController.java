package com.asier.SistemaReservas.airline.controller;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.domain.records.AirlineRequest;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.service.AirlineService;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.system.auth.records.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AirlineController {
    private final AirlineService airlineService;

    @PostMapping(path = "/airline")
    public AirlineDTO createAirline(@RequestBody AirlineRequest request){
        return airlineService.createAirline(request);
    }

    @GetMapping(path = "/airline/{id}/employees")
    public List<AirlineEmployeeInfoDTO> getEmployeesFromAirline(@PathVariable Long id){
        return airlineService.getEmployeesFromAirline(id);
    }

    @PostMapping(path = "/airline/{id}/employees")
    public TokenResponse createAirlineEmployee(@PathVariable Long id, @RequestBody RegisterRequest request){
        return airlineService.createAirlineEmployee(id,request);
    }
}
