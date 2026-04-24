package com.asier.SistemaReservas.airlineEmployees.objects;

import com.asier.SistemaReservas.airline.AirlineObjects;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.airline.employee.domain.enums.EmployeeType;
import com.asier.SistemaReservas.airport.objects.AirportObjects;
import com.asier.SistemaReservas.users.objects.UserObjects;

public class AirlineEmployeesObjects {
    public static AirlineEmployeeInfoEntity airlineEmployeeInfo1(AirlineEntity airline){
        return AirlineEmployeeInfoEntity.builder()
                .id(null)
                .airport(AirportObjects.airport())
                .airline(airline)
                .type(EmployeeType.WORKER)
                .user(UserObjects.airportEmployee())
                .build();
    }
    public static AirlineEmployeeInfoEntity airlineEmployeeInfo2(AirlineEntity airline){
        return AirlineEmployeeInfoEntity.builder()
                .id(null)
                .airport(AirportObjects.airport())
                .airline(airline)
                .type(EmployeeType.ADMIN)
                .user(UserObjects.airportEmployee1())
                .build();
    }
}
