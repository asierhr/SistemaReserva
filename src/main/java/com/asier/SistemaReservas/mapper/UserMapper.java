package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.UserDTO;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(UserEntity user);
    UserEntity toEntity(UserDTO user);
}
