package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;

import java.time.LocalDate;
import java.util.List;

public interface HotelReservationService{
    HotelReservationDTO createReservation(Long id, List<Long> roomIds, LocalDate checkIn, LocalDate checkOut);
    List<HotelReservationDTO> getUserReservations();
}
