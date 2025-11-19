package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.entities.AirportEmployeeInfoEntity;

public interface AirportEmployeeInfoService {
    void createAirportEmployee(AirportEmployeeInfoEntity airportEmployeeInfo);
    AirportEmployeeInfoEntity getAirportEmployeeInfo();
}
