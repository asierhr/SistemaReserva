package com.asier.SistemaReservas.user.mapper;

import com.asier.SistemaReservas.user.domain.DTO.UserDTO;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(UserEntity user);
    UserEntity toEntity(UserDTO user);
}
