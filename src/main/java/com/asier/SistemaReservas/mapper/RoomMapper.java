package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.entities.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(source = "hotel.id", target = "hotelId")
    RoomDTO toDTO(RoomEntity room);
    RoomEntity toEntity(RoomDTO room);
    List<RoomDTO> toDTOList(List<RoomEntity> rooms);
    List<RoomEntity> toEntityList(List<RoomDTO> room);
}
