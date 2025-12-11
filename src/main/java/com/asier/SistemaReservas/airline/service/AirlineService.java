package com.asier.SistemaReservas.airline.service;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;

import java.util.List;

public interface AirlineService {
    AirlineDTO createAirline(AirlineDTO airlineDTO);
    List<AirlineEmployeeInfoDTO> getEmployeesFromAirline(Long airlineId);
}
