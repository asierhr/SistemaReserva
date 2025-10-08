package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;
import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;

public interface HotelReservationMapper {
    HotelReservationDTO toDTO(HotelReservationEntity hotelReservation);
    HotelReservationEntity toEntity(HotelReservationDTO hotelReservation);
}
