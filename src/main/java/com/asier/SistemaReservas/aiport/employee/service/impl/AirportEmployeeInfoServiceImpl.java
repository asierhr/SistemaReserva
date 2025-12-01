package com.asier.SistemaReservas.aiport.employee.service.impl;

import com.asier.SistemaReservas.aiport.employee.domain.entity.AirportEmployeeInfoEntity;
import com.asier.SistemaReservas.aiport.employee.repository.AirportEmployeeInfoRepository;
import com.asier.SistemaReservas.aiport.employee.service.AirportEmployeeInfoService;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
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
