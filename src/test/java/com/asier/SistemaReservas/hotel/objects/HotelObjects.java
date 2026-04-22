package com.asier.SistemaReservas.hotel.objects;

import com.asier.SistemaReservas.comment.objects.CommentObjects;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.locationObjects.LocationObjects;
import com.asier.SistemaReservas.room.objects.RoomObjects;

import java.util.List;

public class HotelObjects {
    public static HotelEntity hotel1(){
        return HotelEntity.builder()
                .hotelName("Hotel Arts Barcelona")
                .location(LocationObjects.location2())
                .rooms(List.of(
                        RoomObjects.room1(),
                        RoomObjects.room2(),
                        RoomObjects.room3(),
                        RoomObjects.room4(),
                        RoomObjects.room5()
                ))
                .comments(List.of(CommentObjects.comment1()))
                .rating(4.7)
                .stars("5")
                .build();
    }

    public static HotelEntity hotel2(){
        return HotelEntity.builder()
                .hotelName("Hotel W Barcelona")
                .location(LocationObjects.location3())
                .rooms(List.of(
                        RoomObjects.room6(),
                        RoomObjects.room7(),
                        RoomObjects.room8(),
                        RoomObjects.room9(),
                        RoomObjects.room10()
                ))
                .comments(List.of(CommentObjects.comment2()))
                .rating(4.6)
                .stars("5")
                .build();
    }

}
