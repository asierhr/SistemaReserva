package com.asier.SistemaReservas.aiport.employee.service;

import com.asier.SistemaReservas.aiport.employee.domain.entity.AirportEmployeeInfoEntity;

public interface AirportEmployeeInfoService {
    void createAirportEmployee(AirportEmployeeInfoEntity airportEmployeeInfo);
    AirportEmployeeInfoEntity getAirportEmployeeInfo();
}
