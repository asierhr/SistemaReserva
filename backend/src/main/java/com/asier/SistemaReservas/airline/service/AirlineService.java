package com.asier.SistemaReservas.airline.service;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.domain.records.AirlineRequest;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.system.auth.records.TokenResponse;

import java.util.List;

public interface AirlineService {
    AirlineDTO createAirline(AirlineRequest request);
    List<AirlineEmployeeInfoDTO> getEmployeesFromAirline(Long airlineId);
    TokenResponse createAirlineEmployee(Long airlineId, RegisterRequest request);
}
