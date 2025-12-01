package com.asier.SistemaReservas.reservation.hotelReservation.service;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;

import java.time.LocalDate;
import java.util.List;

public interface HotelReservationService{
    HotelReservationDTO createReservation(Long id, List<Long> roomIds, LocalDate checkIn, LocalDate checkOut);
    List<HotelReservationDTO> getUserReservations();
}
