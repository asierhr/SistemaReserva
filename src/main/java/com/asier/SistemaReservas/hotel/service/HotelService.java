package com.asier.SistemaReservas.hotel.service;


import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelDTO;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;

public interface HotelService {
    HotelDTO createHotel(HotelDTO hotel);
    HotelSummaryDTO getHotel(Long id);
    HotelEntity getHotelEntity(Long id);
    boolean existsHotel(Long id);
    void updateRating(HotelEntity hotel);
}
