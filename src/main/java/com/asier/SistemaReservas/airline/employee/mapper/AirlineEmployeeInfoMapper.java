package com.asier.SistemaReservas.airline.employee.mapper;

import com.asier.SistemaReservas.aiport.mapper.AirportMapper;
import com.asier.SistemaReservas.airline.employee.domain.DTO.AirlineEmployeeInfoDTO;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.airline.mapper.AirlineMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AirlineMapper.class, AirportMapper.class})
public interface AirlineEmployeeInfoMapper {
    AirlineEmployeeInfoEntity toEntity(AirlineEmployeeInfoDTO airlineEmployeeInfoDTO);
    AirlineEmployeeInfoDTO toDTO(AirlineEmployeeInfoEntity airlineEmployeeInfo);
    List<AirlineEmployeeInfoDTO> toDTOList(List<AirlineEmployeeInfoEntity> airlineEmployeeInfos);
}
