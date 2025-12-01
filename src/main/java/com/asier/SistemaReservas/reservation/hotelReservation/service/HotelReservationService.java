package com.asier.SistemaReservas.reservation.hotelReservation.service;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.records.ReservationHotelRequest;

import java.time.LocalDate;
import java.util.List;

public interface HotelReservationService{
    HotelReservationDTO createReservation(Long id, ReservationHotelRequest request);
    List<HotelReservationDTO> getUserReservations();
    HotelReservationEntity getReservationById(Long id);
}
