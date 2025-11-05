package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelHistoryDTO;
import com.asier.SistemaReservas.domain.entities.HotelHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, UserMapper.class})
public interface HotelHistoryMapper {
    HotelHistoryDTO toDTO(HotelHistoryEntity hotelHistory);
}
