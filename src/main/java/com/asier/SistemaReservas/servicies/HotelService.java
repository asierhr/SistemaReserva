package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.HotelDTO;
import com.asier.SistemaReservas.domain.dto.HotelSummaryDTO;
import com.asier.SistemaReservas.domain.entities.HotelEntity;


public interface HotelService {
    HotelDTO createHotel(HotelDTO hotel);
    HotelSummaryDTO getHotel(Long id);
    HotelEntity getHotelEntity(Long id);
    boolean existsHotel(Long id);
    void updateRating(HotelEntity hotel);
}
