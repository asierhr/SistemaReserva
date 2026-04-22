package com.asier.SistemaReservas.airline.service.impl;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airline.domain.records.AirlineRequest;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.employee.domain.enums.EmployeeType;
import com.asier.SistemaReservas.airline.employee.service.AirlineEmployeeInfoService;
import com.asier.SistemaReservas.airline.mapper.AirlineMapper;
import com.asier.SistemaReservas.airline.repository.AirlineRepository;
import com.asier.SistemaReservas.airline.service.AirlineService;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.system.auth.records.TokenResponse;
import com.asier.SistemaReservas.system.auth.service.AuthService;
import com.asier.SistemaReservas.user.domain.enums.UserRole;
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
    private final AuthService authService;

    @Override
    public AirlineDTO createAirline(AirlineRequest request) {
        AirlineEntity airline = airlineMapper.toEntity(request.airlineDTO());
        AirlineEntity savedAirline = airlineRepository.save(airline);
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name(airline.getName()+"Admin")
                .mail(request.adminEmail())
                .password("admin")
                .role(UserRole.AIRLINE_WORKER)
                .employeeType(EmployeeType.ADMIN)
                .airlineId(request.airlineDTO().getId())
                .build();
        authService.register(registerRequest);

        return airlineMapper.toDTO(savedAirline);
    }

    @Override
    public List<AirlineEmployeeInfoDTO> getEmployeesFromAirline(Long airlineId) {
        AirlineEntity airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline not found"));
        return airlineEmployeeInfoService.transformToDTOList(airline.getEmployees());
    }

    @Override
    public TokenResponse createAirlineEmployee(Long airlineId, RegisterRequest request) {
        request.setAirlineId(airlineId);
        return authService.register(request);
    }
}
