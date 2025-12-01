package com.asier.SistemaReservas.hotel.history.service;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.history.domain.DTO.HotelHistoryDTO;

public interface HotelHistoryService {
    void createHotelHistory();
    void updateHotelHistory(HotelEntity hotel);
    HotelHistoryDTO getHotelHistory();
    boolean existsHotelHistory();
}
