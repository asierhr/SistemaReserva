package com.asier.SistemaReservas.reservation.hotelReservation.service.impl;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.mapper.HotelReservationMapper;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReservationHelperImpl implements HotelReservationHelper {
    private final HotelReservationMapper hotelReservationMapper;

    public List<HotelReservationDTO> transformToDTOList(List<HotelReservationEntity> hotelReservations){
        return hotelReservationMapper.toDTOList(hotelReservations);
    }
}
