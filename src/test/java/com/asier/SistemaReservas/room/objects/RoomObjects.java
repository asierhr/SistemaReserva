package com.asier.SistemaReservas.room.objects;

import com.asier.SistemaReservas.hotel.objects.HotelObjects;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import com.asier.SistemaReservas.room.domain.enums.RoomType;

import java.math.BigDecimal;

public class RoomObjects {
    public static RoomEntity room1(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("10C")
                .type(RoomType.INDIVIDUAL)
                .hotel(HotelObjects.hotel1())
                .costPerNight(new BigDecimal(100))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room2(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("32B")
                .type(RoomType.DOUBLE)
                .hotel(HotelObjects.hotel1())
                .costPerNight(new BigDecimal(150))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room3(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("102D")
                .type(RoomType.SUITE)
                .hotel(HotelObjects.hotel1())
                .costPerNight(new BigDecimal(300))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room4(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("24N")
                .type(RoomType.TRIPLE)
                .hotel(HotelObjects.hotel1())
                .costPerNight(new BigDecimal(200))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room5(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("202S")
                .type(RoomType.PRESIDENTIAL_SUITE)
                .hotel(HotelObjects.hotel1())
                .costPerNight(new BigDecimal(550))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room6(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("10C")
                .type(RoomType.INDIVIDUAL)
                .hotel(HotelObjects.hotel2())
                .costPerNight(new BigDecimal(100))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room7(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("32B")
                .type(RoomType.DOUBLE)
                .hotel(HotelObjects.hotel2())
                .costPerNight(new BigDecimal(150))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room8(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("102D")
                .type(RoomType.SUITE)
                .hotel(HotelObjects.hotel2())
                .costPerNight(new BigDecimal(300))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room9(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("24N")
                .type(RoomType.TRIPLE)
                .hotel(HotelObjects.hotel2())
                .costPerNight(new BigDecimal(200))
                .reservations(null)
                .available(true)
                .build();
    }
    public static RoomEntity room10(){
        return RoomEntity.builder()
                .id(null)
                .numRoom("202S")
                .type(RoomType.PRESIDENTIAL_SUITE)
                .hotel(HotelObjects.hotel2())
                .costPerNight(new BigDecimal(550))
                .reservations(null)
                .available(true)
                .build();
    }
}
