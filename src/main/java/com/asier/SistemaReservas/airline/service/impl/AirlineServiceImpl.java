package com.asier.SistemaReservas.airline.service.impl;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.employee.service.AirlineEmployeeInfoService;
import com.asier.SistemaReservas.airline.mapper.AirlineMapper;
import com.asier.SistemaReservas.airline.repository.AirlineRepository;
import com.asier.SistemaReservas.airline.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;
    private final AirlineEmployeeInfoService airlineEmployeeInfoService;

    @Override
    public AirlineDTO createAirline(AirlineDTO airlineDTO) {
        AirlineEntity airline = airlineMapper.toEntity(airlineDTO);
        AirlineEntity savedAirline = airlineRepository.save(airline);
        return airlineMapper.toDTO(savedAirline);
    }

    @Override
    public List<AirlineEmployeeInfoDTO> getEmployeesFromAirline(Long airlineId) {
        AirlineEntity airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found"));
        return airlineEmployeeInfoService.transformToDTOList(airline.getEmployees());
    }


}
