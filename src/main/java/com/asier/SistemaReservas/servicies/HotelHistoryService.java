package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.HotelHistoryDTO;
import com.asier.SistemaReservas.domain.entities.HotelEntity;

public interface HotelHistoryService {
    void createHotelHistory();
    void updateHotelHistory(HotelEntity hotel);
    HotelHistoryDTO getHotelHistory();
    boolean existsHotelHistory();
}
