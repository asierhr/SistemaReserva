package com.asier.SistemaReservas.search.hotelSearch.service;

import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import java.util.List;

public interface HotelSearchService {
    void saveHotelSearch(HotelSearchDTO hotelSearchDTO, String ipAddress);
    List<HotelSearchDTO> getHotelSearchByUser();
}
