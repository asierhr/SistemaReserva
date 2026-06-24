package com.asier.SistemaReservas.flight.mapper;


import com.asier.SistemaReservas.aiport.mapper.AirportMapper;
import com.asier.SistemaReservas.airline.mapper.AirlineMapper;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightSummaryDTO;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.seats.mapper.SeatMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", uses = {SeatMapper.class, AirportMapper.class, AirlineMapper.class})
public interface FlightMapper {
    FlightDTO toDTO(FlightEntity flight);
    List<FlightDTO> toDTOList(List<FlightEntity> flights);
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    FlightEntity toEntity(FlightDTO dto);
    FlightSummaryDTO toSummaryDTO(FlightEntity flight);
    FlightEntity toEntity(FlightSummaryDTO flight);
    List<FlightSummaryDTO> toSummaryDTOList(List<FlightEntity> flights);
    List<FlightEntity> toEntityList(List<FlightSummaryDTO> flights);
}
