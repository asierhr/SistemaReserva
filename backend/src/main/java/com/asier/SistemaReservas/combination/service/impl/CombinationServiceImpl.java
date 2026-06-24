package com.asier.SistemaReservas.combination.service.impl;

import com.asier.SistemaReservas.combination.domain.dto.CombinationDTO;
import com.asier.SistemaReservas.combination.service.CombinationService;
import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.flight.service.FlightService;
import com.asier.SistemaReservas.room.domain.DTO.RoomCombination;
import com.asier.SistemaReservas.room.service.RoomService;
import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.search.hotelSearch.domain.dto.HotelSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CombinationServiceImpl implements CombinationService {
    private final FlightService flightService;
    private final RoomService roomService;

    @Override
    public CombinationDTO getCombinationBySearch(FlightSearchDTO flightSearchDTO, String ipAddress) {
        HotelSearchDTO hotelSearch = HotelSearchDTO.builder()
                .city(flightSearchDTO.getDestination())
                .checkIn(flightSearchDTO.getDepartureDay())
                .checkOut(flightSearchDTO.getReturnDay())
                .guests(flightSearchDTO.getPassengers())
                .build();
        CompletableFuture<Set<RoomCombination>> hotelsFuture =
                CompletableFuture.supplyAsync(() -> roomService.getRoomsBySearch(hotelSearch, ipAddress));

        CompletableFuture<List<FlightPairDTO>> flightsFuture =
                CompletableFuture.supplyAsync(() -> flightService.getFlightsBySearch(flightSearchDTO, ipAddress));

        return CombinationDTO.builder()
                .flightsPairDTO(flightsFuture.join())
                .hotels(hotelsFuture.join())
                .build();
    }
}
