package com.asier.SistemaReservas.comment.objects;

import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import com.asier.SistemaReservas.hotel.objects.HotelObjects;
import com.asier.SistemaReservas.users.objects.UserObjects;

public class CommentObjects {
    public static CommentEntity comment1(){
        return CommentEntity.builder()
                .id(null)
                .hotel(HotelObjects.hotel1())
                .rating(4.5)
                .comment("Muy buen servicio")
                .user(UserObjects.user())
                .build();
    }
    public static CommentEntity comment2(){
        return CommentEntity.builder()
                .id(null)
                .hotel(HotelObjects.hotel2())
                .rating(4.5)
                .comment("Muy buen servicio")
                .user(UserObjects.user())
                .build();
    }
}
