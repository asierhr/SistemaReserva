package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.AirportEmployeeInfoEntity;
import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.repositories.AirportEmployeeInfoRepository;
import com.asier.SistemaReservas.servicies.AirportEmployeeInfoService;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportEmployeeInfoServiceImpl implements AirportEmployeeInfoService {
    private final AirportEmployeeInfoRepository airportEmployeeInfoRepository;
    private final UserService userService;

    @Override
    public void createAirportEmployee(AirportEmployeeInfoEntity airportEmployeeInfo) {
        airportEmployeeInfoRepository.save(airportEmployeeInfo);
    }

    @Override
    public AirportEmployeeInfoEntity getAirportEmployeeInfo() {
        UserEntity user = userService.getUserEntity();
        return airportEmployeeInfoRepository.findByUserId(user.getId());
    }
}
