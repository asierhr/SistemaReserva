package com.asier.SistemaReservas.airport.objects;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.locationObjects.LocationObjects;

public class AirportObjects {
    public static AirportEntity airport(){
        return AirportEntity.builder()
                .id(null)
                .airportName("Barajas")
                .location(LocationObjects.location1())
                .build();
    }
    public static AirportEntity airport1(){
        return AirportEntity.builder()
                .id(null)
                .airportName("El Prat")
                .location(LocationObjects.location2())
                .build();
    }
}
