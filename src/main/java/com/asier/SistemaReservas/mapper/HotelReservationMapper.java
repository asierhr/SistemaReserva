package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;
import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;

import java.util.List;

public interface HotelReservationMapper {
    HotelReservationDTO toDTO(HotelReservationEntity hotelReservation);
    HotelReservationEntity toEntity(HotelReservationDTO hotelReservation);
    List<HotelReservationDTO> toDTOList(List<HotelReservationEntity> hotelReservation);
}
