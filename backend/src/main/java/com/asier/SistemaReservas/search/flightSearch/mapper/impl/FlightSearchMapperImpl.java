package com.asier.SistemaReservas.search.flightSearch.mapper.impl;

import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.search.flightSearch.domain.entity.FlightSearchEntity;
import com.asier.SistemaReservas.search.flightSearch.mapper.FlightSearchMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightSearchMapperImpl implements FlightSearchMapper {
    private final UserMapper userMapper;

    @Override
    public FlightSearchEntity toEntity(FlightSearchDTO flightSearchDTO) {
        FlightSearchEntity flightSearch = FlightSearchEntity.builder()
                .destination(flightSearchDTO.getDestination())
                .origin(flightSearchDTO.getOrigin())
                .passengers(flightSearchDTO.getPassengers())
                .departureDay(flightSearchDTO.getDepartureDay())
                .returnDay(flightSearchDTO.getReturnDay())
                .maxStops(flightSearchDTO.getMaxStops())
                .build();
        return flightSearch;
    }

    @Override
    public FlightSearchDTO toDTO(FlightSearchEntity flightSearch) {
        FlightSearchDTO flightSearchDTO = FlightSearchDTO.builder()
                .destination(flightSearch.getDestination())
                .origin(flightSearch.getOrigin())
                .passengers(flightSearch.getPassengers())
                .departureDay(flightSearch.getDepartureDay())
                .returnDay(flightSearch.getReturnDay())
                .maxStops(flightSearch.getMaxStops())
                .build();
        return flightSearchDTO;
    }

    @Override
    public List<FlightSearchDTO> toDTOList(List<FlightSearchEntity> flightSearchList) {
        List<FlightSearchDTO> flightSearchDTOS = new ArrayList<>();
        for(FlightSearchEntity flightSearch: flightSearchList){
            flightSearchDTOS.add(toDTO(flightSearch));
        }
        return flightSearchDTOS;
    }
}
