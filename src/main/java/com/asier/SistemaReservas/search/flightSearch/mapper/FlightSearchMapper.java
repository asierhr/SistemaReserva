package com.asier.SistemaReservas.search.flightSearch.mapper;

import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.search.flightSearch.domain.entity.FlightSearchEntity;

import java.util.List;

public interface FlightSearchMapper {
    FlightSearchEntity toEntity(FlightSearchDTO flightSearchDTO);
    FlightSearchDTO toDTO(FlightSearchEntity flightSearch);
    List<FlightSearchDTO> toDTOList(List<FlightSearchEntity> flightSearch);
}
