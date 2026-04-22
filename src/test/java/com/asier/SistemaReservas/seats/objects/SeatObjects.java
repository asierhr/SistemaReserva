package com.asier.SistemaReservas.seats.objects;

import com.asier.SistemaReservas.flight.objects.FlightObjects;
import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;
import com.asier.SistemaReservas.seats.domain.enums.SeatType;

import java.math.BigDecimal;

public class SeatObjects {
    public static SeatEntity seat1() {
        return SeatEntity.builder()
                .seatClass(SeatClass.ECONOMY)
                .seatType(SeatType.WINDOW)
                .seatRow(1)
                .seatColumn("1A")
                .seatNumber("A")
                .costPerSeat(new BigDecimal(100))
                .flight(FlightObjects.flight1())
                .available(true)
                .build();
    }

    public static SeatEntity seat1WithId() {
        return SeatEntity.builder()
                .id(1L)
                .seatClass(SeatClass.ECONOMY)
                .seatType(SeatType.WINDOW)
                .seatRow(1)
                .seatColumn("1A")
                .seatNumber("A")
                .costPerSeat(new BigDecimal(100))
                .flight(FlightObjects.flight1())
                .available(true)
                .build();
    }

    public static SeatDTO seatDTO1() {
        return SeatDTO.builder()
                .seatClass(SeatClass.ECONOMY)
                .seatType(SeatType.WINDOW)
                .seatRow(1)
                .seatColumn("1A")
                .seatNumber("A")
                .costPerSeat(new BigDecimal(100))
                .flightId(FlightObjects.flight1().getId())
                .available(true)
                .build();
    }

    public static SeatDTO seatDTO1WithId() {
        return SeatDTO.builder()
                .id(1L)
                .seatClass(SeatClass.ECONOMY)
                .seatType(SeatType.WINDOW)
                .seatRow(1)
                .seatColumn("1A")
                .seatNumber("A")
                .costPerSeat(new BigDecimal(100))
                .available(true)
                .flightId(FlightObjects.flight1().getId())
                .build();
    }

    public static SeatEntity seat2() {
        return SeatEntity.builder()
                .seatClass(SeatClass.BUSINESS)
                .seatType(SeatType.AISLE)
                .seatRow(2)
                .seatColumn("2B")
                .seatNumber("B")
                .costPerSeat(new BigDecimal(1000))
                .available(true)
                .flight(FlightObjects.flight1())
                .build();
    }

    public static SeatEntity seat2WithId() {
        return SeatEntity.builder()
                .id(2L)
                .seatClass(SeatClass.BUSINESS)
                .seatType(SeatType.AISLE)
                .seatRow(2)
                .seatColumn("2B")
                .seatNumber("B")
                .costPerSeat(new BigDecimal(1000))
                .available(true)
                .flight(FlightObjects.flight1())
                .build();
    }

    public static SeatDTO seatDTO2() {
        return SeatDTO.builder()
                .seatClass(SeatClass.BUSINESS)
                .seatType(SeatType.AISLE)
                .seatRow(2)
                .seatColumn("2B")
                .seatNumber("B")
                .costPerSeat(new BigDecimal(1000))
                .available(true)
                .flightId(FlightObjects.flight1().getId())
                .build();
    }

    public static SeatDTO seatDTO2WithId() {
        return SeatDTO.builder()
                .id(2L)
                .seatClass(SeatClass.BUSINESS)
                .seatType(SeatType.AISLE)
                .seatRow(2)
                .seatColumn("2B")
                .seatNumber("B")
                .costPerSeat(new BigDecimal(1000))
                .available(true)
                .flightId(FlightObjects.flight1().getId())
                .build();
    }
    public static SeatEntity seat3(){
        return SeatEntity.builder()
                .id(null)
                .seatClass(SeatClass.PREMIUM)
                .seatType(SeatType.MIDDLE)
                .seatRow(3)
                .seatColumn("3C")
                .seatNumber("C")
                .costPerSeat(new BigDecimal(100))
                .available(true)
                .flight(FlightObjects.flight1())
                .build();
    }
    public static SeatEntity seat3WithId(){
        return SeatEntity.builder()
                .id(3L)
                .seatClass(SeatClass.PREMIUM)
                .seatType(SeatType.MIDDLE)
                .seatRow(3)
                .seatColumn("3C")
                .seatNumber("C")
                .costPerSeat(new BigDecimal(100))
                .available(true)
                .flight(FlightObjects.flight1())
                .build();
    }
    public static SeatDTO seatDTO3(){
        return SeatDTO.builder()
                .id(null)
                .seatClass(SeatClass.PREMIUM)
                .seatType(SeatType.MIDDLE)
                .seatRow(3)
                .seatColumn("3C")
                .seatNumber("C")
                .costPerSeat(new BigDecimal(100))
                .available(true)
                .flightId(FlightObjects.flight1().getId())
                .build();
    }
    public static SeatDTO seatDTO3WithId(){
        return SeatDTO.builder()
                .id(3L)
                .seatClass(SeatClass.PREMIUM)
                .seatType(SeatType.MIDDLE)
                .seatRow(3)
                .seatColumn("3C")
                .seatNumber("C")
                .costPerSeat(new BigDecimal(100))
                .available(true)
                .flightId(FlightObjects.flight1().getId())
                .build();
    }
}
