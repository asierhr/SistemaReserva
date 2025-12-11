package com.asier.SistemaReservas.search.hotelSearch.mapper.impl;

import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import com.asier.SistemaReservas.search.hotelSearch.domain.entity.HotelSearchEntity;
import com.asier.SistemaReservas.search.hotelSearch.mapper.HotelSearchMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelSearchMapperImpl implements HotelSearchMapper {
    private final UserMapper userMapper;

    @Override
    public HotelSearchEntity toEntity(HotelSearchDTO hotelSearchDTO) {
        HotelSearchEntity hotelSearch = HotelSearchEntity.builder()
                .city(hotelSearchDTO.getCity())
                .checkIn(hotelSearchDTO.getCheckIn())
                .checkOut(hotelSearchDTO.getCheckOut())
                .guests(hotelSearchDTO.getGuests())
                .build();
        return hotelSearch;
    }

    @Override
    public HotelSearchDTO toDTO(HotelSearchEntity hotelSearch) {
        HotelSearchDTO hotelSearchDTO = HotelSearchDTO.builder()
                .city(hotelSearch.getCity())
                .checkIn(hotelSearch.getCheckIn())
                .checkOut(hotelSearch.getCheckOut())
                .guests(hotelSearch.getGuests())
                .build();
        return hotelSearchDTO;
    }

    @Override
    public List<HotelSearchDTO> toDTOList(List<HotelSearchEntity> hotelSearchList) {
        List<HotelSearchDTO> hotelSearchDTOS = new ArrayList<>();
        for(HotelSearchEntity hotelSearch: hotelSearchList){
            hotelSearchDTOS.add(toDTO(hotelSearch));
        }
        return hotelSearchDTOS;
    }
}
