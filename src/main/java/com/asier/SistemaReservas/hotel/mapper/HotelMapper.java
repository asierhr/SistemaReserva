package com.asier.SistemaReservas.hotel.mapper;

import com.asier.SistemaReservas.hotel.domain.DTO.HotelDTO;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.room.mapper.RoomMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public interface HotelMapper {
    HotelDTO toDTO(HotelEntity hotel);
    HotelEntity toEntity(HotelDTO hotel);
    HotelSummaryDTO toSummaryDTO(HotelEntity hotel);
    HotelEntity toEntity(HotelSummaryDTO hotel);
}
