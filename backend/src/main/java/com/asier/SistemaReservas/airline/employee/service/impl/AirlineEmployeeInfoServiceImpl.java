package com.asier.SistemaReservas.airline.employee.service.impl;

import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.airline.employee.mapper.AirlineEmployeeInfoMapper;
import com.asier.SistemaReservas.airline.employee.repository.AirlineEmployeeInfoRepository;
import com.asier.SistemaReservas.airline.employee.service.AirlineEmployeeInfoService;
import com.asier.SistemaReservas.airline.mapper.AirlineMapper;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineEmployeeInfoServiceImpl implements AirlineEmployeeInfoService {
    private final AirlineEmployeeInfoRepository airportEmployeeInfoRepository;
    private final UserService userService;
    private final AirlineEmployeeInfoMapper airlineEmployeeInfoMapper;

    @Override
    public void createAirlineEmployee(AirlineEmployeeInfoEntity airportEmployeeInfo) {
        airportEmployeeInfoRepository.save(airportEmployeeInfo);
    }

    @Override
    public AirlineEmployeeInfoEntity getAirlineEmployeeInfo() {
        UserEntity user = userService.getUserEntity();
        return airportEmployeeInfoRepository.findByUserId(user.getId());
    }

    @Override
    public AirlineEmployeeInfoDTO getAirlineEmployee() {
        return airlineEmployeeInfoMapper.toDTO(getAirlineEmployeeInfo());
    }

    @Override
    public List<AirlineEmployeeInfoDTO> transformToDTOList(List<AirlineEmployeeInfoEntity> airlineEmployeeInfos) {
        return airlineEmployeeInfoMapper.toDTOList(airlineEmployeeInfos);
    }
}
