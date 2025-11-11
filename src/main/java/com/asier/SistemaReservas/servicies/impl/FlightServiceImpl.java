package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.FlightDTO;
import com.asier.SistemaReservas.domain.dto.FlightPairDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.entities.FlightEntity;
import com.asier.SistemaReservas.domain.records.FlightSearch;
import com.asier.SistemaReservas.mapper.FlightMapper;
import com.asier.SistemaReservas.repositories.FlightRepository;
import com.asier.SistemaReservas.servicies.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO createFlight(FlightDTO flight) {
        FlightEntity flightEntity = flightMapper.toEntity(flight);
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

        List<FlightEntity> outboundFlights = flightRepository.getFlightsByFlightSearch(
                flightSearch.origin(),
                flightSearch.destination(),
                flightSearch.departureDay()
        );

        List<FlightEntity> returnFlights = flightRepository.getFlightsByFlightSearch(
                flightSearch.destination(),
                flightSearch.origin(),
                flightSearch.returnDay()
        );

        List<FlightSummaryDTO> outboundDTOs = flightMapper.toSummaryDTOList(outboundFlights);
        List<FlightSummaryDTO> returnDTOs = flightMapper.toSummaryDTOList(returnFlights);

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
