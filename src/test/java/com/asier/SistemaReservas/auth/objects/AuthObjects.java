package com.asier.SistemaReservas.auth.objects;

import com.asier.SistemaReservas.system.auth.records.LoginRequest;
import com.asier.SistemaReservas.system.auth.records.RegisterRequest;
import com.asier.SistemaReservas.user.domain.enums.UserRole;

public class AuthObjects {
    public static RegisterRequest valid(){
        return RegisterRequest.builder()
                .mail("testObject@gmail.com")
                .name("testObject")
                .password("testObject")
                .role(UserRole.USER)
                .build();
    }

    public static LoginRequest login(){
        return new LoginRequest("testObject@gmail.com", "testPassword");
    }

    public static LoginRequest login1(){
        return new LoginRequest("testObject1@gmail.com", "testPassword1");
    }
}
