package com.asier.SistemaReservas.search.hotelSearch.mapper;

import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import com.asier.SistemaReservas.search.hotelSearch.domain.entity.HotelSearchEntity;

import java.util.List;

public interface HotelSearchMapper {
    HotelSearchDTO toDTO(HotelSearchEntity hotelSearch);
    HotelSearchEntity toEntity(HotelSearchDTO hotelSearchDTO);
    List<HotelSearchDTO> toDTOList(List<HotelSearchEntity> hotelSearchList);
}
