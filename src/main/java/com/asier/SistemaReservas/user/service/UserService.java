package com.asier.SistemaReservas.user.service;

import com.asier.SistemaReservas.user.domain.DTO.UserDTO;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserByMail(String mail);
    UserEntity createUser(UserEntity user);
    UserDTO getUser();
    UserEntity getUserEntity();
    UserEntity getUserById(Long id);
}
