package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.UserDTO;
import com.asier.SistemaReservas.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserByMail(String mail);
    UserEntity createUser(UserEntity user);
    UserDTO getUser();
    UserEntity getUserEntity();
    UserEntity getUserById(Long id);
}
