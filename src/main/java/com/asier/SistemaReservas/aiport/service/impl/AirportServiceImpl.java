package com.asier.SistemaReservas.aiport.service.impl;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.aiport.employee.domain.entity.AirportEmployeeInfoEntity;
import com.asier.SistemaReservas.aiport.employee.service.AirportEmployeeInfoService;
import com.asier.SistemaReservas.aiport.mapper.AirportMapper;
import com.asier.SistemaReservas.aiport.repository.AirportRepository;
import com.asier.SistemaReservas.aiport.service.AirportService;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;
    private final AirportEmployeeInfoService airportEmployeeInfoService;
    private final FlightService flightService;


    @Override
    public AirportDTO createAirport(AirportDTO airportDTO) {
        AirportEntity airport = AirportEntity.builder()
                .location(airportDTO.getLocation())
                .airportName(airportDTO.getAirportName())
                .build();
        return airportMapper.toDTO(airportRepository.save(airport));
    }

    @Override
    public AirportEntity getAirport(Long id) {
        return airportRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public AirportDTO getAirportByName(String name) {
        return airportMapper.toDTO(airportRepository.findByAirportName(name).orElseThrow());
    }

    @Override
    public AirportEntity getAirportByLocationCity(String city) {
        return airportRepository.findByLocationCity(city);
    }

    @Override
    public Map<String, List<FlightDTO>> getTodayFlights() {
        AirportEmployeeInfoEntity airportEmployeeInfo = airportEmployeeInfoService.getAirportEmployeeInfo();
        if(airportEmployeeInfo == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have no rights to do this operation");
        AirportEntity airport = airportEmployeeInfo.getAirport();

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(23, 59, 59);

        List<FlightEntity> allFlights = new ArrayList<>();
        List<FlightEntity> arrivingFlights = airport.getArrivingFlights().stream()
                .filter(f -> {
                    LocalDateTime arrivalDateTime = LocalDateTime.of(f.getFlightDay(), f.getArrivalTime());
                    if (arrivalDateTime.isBefore(startOfToday)) {
                        arrivalDateTime = arrivalDateTime.plusDays(1);
                    }
                    return !arrivalDateTime.isBefore(startOfToday) && !arrivalDateTime.isAfter(endOfToday);
                })
                .sorted(Comparator.comparing(FlightEntity::getFlightDay)
                        .thenComparing(f -> f.getArrivalTime() != null ? f.getArrivalTime() : LocalTime.MIDNIGHT))
                .toList();
        List<FlightEntity> departureFlights = airport.getDepartingFlights().stream()
                .filter(f -> f.getFlightDay().equals(today))
                .sorted(Comparator.comparing(FlightEntity::getFlightDay)
                        .thenComparing(f -> f.getDepartureTime() != null ? f.getDepartureTime() : LocalTime.MIDNIGHT))
                .toList();

        Map<String, List<FlightDTO>> flightsMap = new HashMap<>();
        flightsMap.put("arrivals", flightService.transformListEntity(arrivingFlights));
        flightsMap.put("departures", flightService.transformListEntity(departureFlights));

        return flightsMap;
    }
}
