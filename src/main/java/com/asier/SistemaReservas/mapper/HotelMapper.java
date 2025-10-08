package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelDTO;
import com.asier.SistemaReservas.domain.dto.HotelSummaryDTO;
import com.asier.SistemaReservas.domain.entities.HotelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public interface HotelMapper {
    HotelDTO toDTO(HotelEntity hotel);
    HotelEntity toEntity(HotelDTO hotel);
    HotelSummaryDTO toSummaryDTO(HotelEntity hotel);
    HotelEntity toEntity(HotelSummaryDTO hotel);
}
