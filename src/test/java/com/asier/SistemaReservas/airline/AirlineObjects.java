package com.asier.SistemaReservas.airline;

import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.airlineEmployees.objects.AirlineEmployeesObjects;

import java.time.LocalDateTime;
import java.util.List;

public class AirlineObjects {
    public static AirlineEntity airline1(){
        return AirlineEntity.builder()
                .id(null)
                .country("Spain")
                .name("Iberia")
                .createdAt(LocalDateTime.now())
                .iataCode("IB")
                .icaoCode("IBE")
                .employees(List.of(AirlineEmployeesObjects.airlineEmployeeInfo1(),AirlineEmployeesObjects.airlineEmployeeInfo2()))
                .build();
    }
}
