package com.asier.SistemaReservas.hotel.history.mapper;

import com.asier.SistemaReservas.hotel.history.domain.DTO.HotelHistoryDTO;
import com.asier.SistemaReservas.hotel.history.domain.entity.HotelHistoryEntity;
import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {HotelMapper.class, UserMapper.class})
public interface HotelHistoryMapper {
    HotelHistoryDTO toDTO(HotelHistoryEntity hotelHistory);
}
