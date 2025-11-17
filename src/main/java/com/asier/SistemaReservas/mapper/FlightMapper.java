package com.asier.SistemaReservas.mapper;


import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.entities.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", uses = {SeatMapper.class, AirportMapper.class})
public interface FlightMapper {
    FlightDTO toDTO(FlightEntity flight);
    List<FlightDTO> toDTOList(List<FlightEntity> flights);
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    FlightEntity toEntity(FlightDTO dto);
    FlightSummaryDTO toSummaryDTO(FlightEntity flight);
    FlightEntity toEntity(FlightSummaryDTO flight);
    List<FlightSummaryDTO> toSummaryDTOList(List<FlightEntity> flights);
}
