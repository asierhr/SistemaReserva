package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.HotelEmployeeInfoEntity;
import com.asier.SistemaReservas.repositories.HotelEmployeeInfoRepository;
import com.asier.SistemaReservas.servicies.HotelEmployeeInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelEmployeeInfoServiceImpl implements HotelEmployeeInfoService {
    private final HotelEmployeeInfoRepository hotelEmployeeInfoRepository;

    @Override
    public void createHotelEmployee(HotelEmployeeInfoEntity hotelEmployeeInfo) {
        hotelEmployeeInfoRepository.save(hotelEmployeeInfo);
    }
}
