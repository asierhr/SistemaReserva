package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.AirportDTO;
import com.asier.SistemaReservas.domain.entities.AirportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    AirportDTO toDTO(AirportEntity airport);

    @Mapping(target = "employees", ignore = true)
    AirportEntity toEntity(AirportDTO airportDTO);
}
