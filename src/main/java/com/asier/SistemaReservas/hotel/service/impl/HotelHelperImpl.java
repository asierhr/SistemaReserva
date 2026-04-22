package com.asier.SistemaReservas.hotel.service.impl;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.repository.HotelRepository;
import com.asier.SistemaReservas.hotel.service.HotelHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelHelperImpl implements HotelHelper {
    private final HotelRepository hotelRepository;

    @Override
    public HotelEntity getHotelEntity(Long id) {
        return hotelRepository.findById(id).orElseThrow();
    }
}
