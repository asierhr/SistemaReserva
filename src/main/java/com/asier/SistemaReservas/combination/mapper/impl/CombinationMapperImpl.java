package com.asier.SistemaReservas.combination.mapper.impl;

import com.asier.SistemaReservas.combination.domain.dto.CombinationDTO;
import com.asier.SistemaReservas.combination.domain.entity.CombinationEntity;
import com.asier.SistemaReservas.combination.mapper.CombinationMapper;
import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.flight.mapper.FlightMapper;
import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CombinationMapperImpl implements CombinationMapper{
    private final FlightMapper flightMapper;
    private final HotelMapper hotelMapper;

    @Override
    public CombinationEntity toEntity(CombinationDTO combinationDTO) {
        CombinationEntity combination = CombinationEntity.builder()
                .outboundFlights(flightMapper.toEntityList(combinationDTO.getFlightPairDTO().getOutbound()))
                .inboundFlights(flightMapper.toEntityList(combinationDTO.getFlightPairDTO().getInbound()))
                .hotel(hotelMapper.toEntity(combinationDTO.getHotelSummaryDTO()))
                .totalPrice(combinationDTO.getFlightPairDTO().getTotalPrice())
                .build();
        return combination;
    }

    @Override
    public CombinationDTO toDTO(CombinationEntity combination) {
        FlightPairDTO flightPairDTO = FlightPairDTO.builder()
                .outbound(flightMapper.toSummaryDTOList(combination.getOutboundFlights()))
                .inbound(flightMapper.toSummaryDTOList(combination.getInboundFlights()))
                .totalPrice(combination.getTotalPrice())
                .build();

        CombinationDTO combinationDTO = CombinationDTO.builder()
                .hotelSummaryDTO(hotelMapper.toSummaryDTO(combination.getHotel()))
                .flightPairDTO(flightPairDTO)
                .build();

        return combinationDTO;
    }
}
