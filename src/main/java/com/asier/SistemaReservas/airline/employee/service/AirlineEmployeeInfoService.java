package com.asier.SistemaReservas.airline.employee.service;

import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;

import java.util.List;

public interface AirlineEmployeeInfoService {
    void createAirlineEmployee(AirlineEmployeeInfoEntity airportEmployeeInfo);
    AirlineEmployeeInfoEntity getAirlineEmployeeInfo();
    AirlineEmployeeInfoDTO getAirlineEmployee();
    List<AirlineEmployeeInfoDTO> transformToDTOList(List<AirlineEmployeeInfoEntity> airlineEmployeeInfos);
}
