package com.asier.SistemaReservas.reservation.hotelReservation.service;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;

import java.util.List;

public interface HotelReservationHelper {
    List<HotelReservationDTO> transformToDTOList(List<HotelReservationEntity> hotelReservations);
}
