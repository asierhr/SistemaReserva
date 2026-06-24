package com.asier.SistemaReservas.airline.mapper;

import com.asier.SistemaReservas.aiport.mapper.AirportMapper;
import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.airline.domain.entity.AirlineEntity;
import com.asier.SistemaReservas.flight.mapper.FlightMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AirportMapper.class, FlightMapper.class})
public interface AirlineMapper {
    AirlineDTO toDTO (AirlineEntity airline);
    AirlineEntity toEntity(AirlineDTO airlineDTO);
}
