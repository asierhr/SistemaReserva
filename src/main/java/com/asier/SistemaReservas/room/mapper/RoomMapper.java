package com.asier.SistemaReservas.room.mapper;

import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
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
