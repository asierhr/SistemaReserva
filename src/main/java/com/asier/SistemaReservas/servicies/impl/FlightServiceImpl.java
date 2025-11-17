package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightPairDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.entities.AirportEntity;
import com.asier.SistemaReservas.domain.entities.FlightEntity;
import com.asier.SistemaReservas.domain.records.FlightSearch;
import com.asier.SistemaReservas.mapper.FlightMapper;
import com.asier.SistemaReservas.repositories.FlightRepository;
import com.asier.SistemaReservas.servicies.AirportService;
import com.asier.SistemaReservas.servicies.FlightSeatHelper;
import com.asier.SistemaReservas.servicies.FlightService;
import com.asier.SistemaReservas.servicies.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final FlightSeatHelper flightSeatHelper;
    private final AirportService airportService;

    @Override
    public FlightDTO createFlight(FlightDTO flight) {
        if(flightRepository.existsByAirlineAndFlightDay(flight.getAirline(), flight.getFlightDay())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        }
        FlightEntity flightEntity = flightMapper.toEntity(flight);
        flightEntity.setOrigin(airportService.getAirport(flight.getOrigin().getId()));
        flightEntity.setDestination(airportService.getAirport(flight.getDestination().getId()));
        flightEntity.getSeats().forEach(seat -> seat.setFlight(flightEntity));
        FlightEntity savedFlight = flightRepository.save(flightEntity);
        return flightMapper.toDTO(savedFlight);
    }

    @Override
    public FlightDTO getFlight(Long id) {
        FlightEntity flight = getFlightEntity(id);
        return flightMapper.toDTO(flight);
    }

    @Override
    public FlightSummaryDTO getSummaryFlight(Long id) {
        FlightEntity flight = flightRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        return flightMapper.toSummaryDTO(flight);
    }

    @Override
    public boolean existsById(Long id) {
        return flightRepository.existsById(id);
    }

    @Override
    public FlightEntity getFlightEntity(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    @Override
    public List<FlightPairDTO> getFlightsBySearch(FlightSearch flightSearch) {

        AirportEntity airportOrigin = airportService.getAirportByLocationCity(flightSearch.origin());
        AirportEntity airportDestination = airportService.getAirportByLocationCity(flightSearch.destination());

        List<FlightEntity> outboundFlights = flightRepository.getFlightsByFlightSearch(
                airportOrigin.getId(),
                airportDestination.getId(),
                flightSearch.departureDay()
        );

        List<FlightEntity> returnFlights = flightRepository.getFlightsByFlightSearch(
                airportDestination.getId(),
                airportOrigin.getId(),
                flightSearch.returnDay()
        );

        List<FlightSummaryDTO> outboundDTOs = flightMapper.toSummaryDTOList(outboundFlights).stream()
                .filter(f ->flightSeatHelper.getAvailableSeatsForFlight(f.getId()).size() >= flightSearch.passengers())
                .toList();
        List<FlightSummaryDTO> returnDTOs = flightMapper.toSummaryDTOList(returnFlights).stream()
                .filter(f -> flightSeatHelper.getAvailableSeatsForFlight(f.getId()).size() >= flightSearch.passengers())
                .toList();

        List<FlightPairDTO> flightPairs = new ArrayList<>();

        for (FlightSummaryDTO outbound : outboundDTOs) {
            for (FlightSummaryDTO inbound : returnDTOs) {
                if (outbound.getAirline().equals(inbound.getAirline()) &&
                        inbound.getFlightDay().isAfter(outbound.getFlightDay())) {
                    flightPairs.add(new FlightPairDTO(outbound, inbound));
                }
            }
        }
        return flightPairs;
    }

    @Override
    public List<String> getAllOrigins() {
        return flightRepository.findAllOrigins();
    }

    @Override
    public List<String> getAllDestinations() {
        return flightRepository.findAllDestinations();
    }
}
