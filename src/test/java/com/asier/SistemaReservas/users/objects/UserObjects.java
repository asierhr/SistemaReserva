package com.asier.SistemaReservas.users.objects;

import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.domain.enums.UserRole;

import java.time.LocalDateTime;

public class UserObjects {
    public static UserEntity user(){
        return UserEntity.builder()
                .id(null)
                .mail("testObject@gmail.com")
                .name("testObject")
                .password("testPassword")
                .userRole(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static UserEntity airportEmployee(){
        return UserEntity.builder()
                .id(null)
                .mail("airportEmployee@gmail.com")
                .name("airportEmployee")
                .password("testPassword")
                .userRole(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static UserEntity airportEmployee1(){
        return UserEntity.builder()
                .id(null)
                .mail("airportEmployee1@gmail.com")
                .name("airportEmployee1")
                .password("testPassword")
                .userRole(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
