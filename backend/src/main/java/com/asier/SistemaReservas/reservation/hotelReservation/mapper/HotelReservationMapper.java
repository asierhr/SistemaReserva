package com.asier.SistemaReservas.reservation.hotelReservation.mapper;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;

import java.util.List;

public interface HotelReservationMapper {
    HotelReservationDTO toDTO(HotelReservationEntity hotelReservation);
    HotelReservationEntity toEntity(HotelReservationDTO hotelReservation);
    List<HotelReservationDTO> toDTOList(List<HotelReservationEntity> hotelReservation);
}
