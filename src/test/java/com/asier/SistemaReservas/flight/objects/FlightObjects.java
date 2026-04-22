package com.asier.SistemaReservas.flight.objects;

import com.asier.SistemaReservas.airline.AirlineObjects;
import com.asier.SistemaReservas.airport.objects.AirportObjects;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.seats.objects.SeatObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FlightObjects {
    public static FlightEntity flight1(){
        return FlightEntity.builder()
                .id(null)
                .origin(AirportObjects.airport())
                .destination(AirportObjects.airport1())
                .flightDay(LocalDate.of(2025, 3, 15))
                .departureTime(LocalTime.of(10,30))
                .arrivalTime(LocalTime.of(11,45))
                .airline(AirlineObjects.airline1())
                .seats(List.of(SeatObjects.seat1(),SeatObjects.seat2(),SeatObjects.seat3()))
                .build();
    }
}
