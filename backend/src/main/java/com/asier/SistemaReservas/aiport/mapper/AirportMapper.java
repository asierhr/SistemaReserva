package com.asier.SistemaReservas.aiport.mapper;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    AirportDTO toDTO(AirportEntity airport);

    @Mapping(target = "employees", ignore = true)
    AirportEntity toEntity(AirportDTO airportDTO);
}
