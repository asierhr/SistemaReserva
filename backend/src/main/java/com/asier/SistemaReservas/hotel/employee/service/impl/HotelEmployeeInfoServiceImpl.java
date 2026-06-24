package com.asier.SistemaReservas.hotel.employee.service.impl;

import com.asier.SistemaReservas.hotel.employee.domain.entity.HotelEmployeeInfoEntity;
import com.asier.SistemaReservas.hotel.employee.repository.HotelEmployeeInfoRepository;
import com.asier.SistemaReservas.hotel.employee.service.HotelEmployeeInfoService;
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
