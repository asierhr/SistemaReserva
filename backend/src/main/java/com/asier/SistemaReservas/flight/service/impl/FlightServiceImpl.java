package com.asier.SistemaReservas.flight.service.impl;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.airline.employee.domain.entity.AirlineEmployeeInfoEntity;
import com.asier.SistemaReservas.airline.employee.service.AirlineEmployeeInfoService;
import com.asier.SistemaReservas.flight.domain.DTO.FlightPairDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightDTO;
import com.asier.SistemaReservas.flight.domain.DTO.FlightSummaryDTO;
import com.asier.SistemaReservas.flight.domain.entity.FlightEntity;
import com.asier.SistemaReservas.search.flightSearch.domain.dto.FlightSearchDTO;
import com.asier.SistemaReservas.flight.mapper.FlightMapper;
import com.asier.SistemaReservas.flight.repository.FlightRepository;
import com.asier.SistemaReservas.flight.service.FlightHelper;
import com.asier.SistemaReservas.flight.service.FlightService;
import com.asier.SistemaReservas.search.flightSearch.service.FlightSearchService;
import com.asier.SistemaReservas.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final FlightHelper flightHelper;
    private final FlightSearchService flightSearchService;
    private final AirlineEmployeeInfoService airlineEmployeeInfoService;

    @Override
    @Transactional
    public FlightDTO createFlight(FlightDTO flight) {
        if(flightRepository.existsFlight(flight.getFlightNumber(),flight.getAirlineDTO().getId(),flight.getFlightDay())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists");
        }

        if(flight.getFlightDay().isBefore(LocalDate.now()) || flight.getOrigin().getId().equals(flight.getDestination().getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create flight");

        AirlineEmployeeInfoEntity airlineEmployeeInfo = airlineEmployeeInfoService.getAirlineEmployeeInfo();

        if(airlineEmployeeInfo == null || !airlineEmployeeInfo.getAirline().getId().equals(flight.getAirlineDTO().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not allowed to create a flight from this airline");
        }

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
    public List<FlightPairDTO> getFlightsBySearch(FlightSearchDTO flightSearch, String ipAddress) {
        flightSearchService.saveSearch(flightSearch, ipAddress);

        AirportEntity airportOrigin = flightHelper.getAirportByLocationCity(flightSearch.getOrigin());
        AirportEntity airportDestination = flightHelper.getAirportByLocationCity(flightSearch.getDestination());

        List<List<FlightEntity>> outboundOptions = findFlightRoutes(
                airportOrigin,
                airportDestination,
                flightSearch.getDepartureDay(),
                flightSearch.getPassengers(),
                flightSearch.getMaxStops()
        );

        List<List<FlightEntity>> returnOptions = findFlightRoutes(
                airportDestination,
                airportOrigin,
                flightSearch.getReturnDay(),
                flightSearch.getPassengers(),
                flightSearch.getMaxStops()
        );

        List<List<FlightSummaryDTO>> outboundDTOs = outboundOptions.stream()
                .map(flightMapper::toSummaryDTOList)
                .toList();

        List<List<FlightSummaryDTO>> returnDTOs = returnOptions.stream()
                .map(flightMapper::toSummaryDTOList)
                .toList();

        return createFlightPairs(outboundDTOs, returnDTOs);
    }

    private List<List<FlightEntity>> findFlightRoutes(
            AirportEntity origin,
            AirportEntity destination,
            LocalDate date,
            Integer passengers,
            Integer maxStops
    ) {
        List<List<FlightEntity>> routes = new ArrayList<>();

        List<FlightEntity> directFlights = findDirectFlights(origin, destination, date, passengers);
        directFlights.forEach(flight -> routes.add(List.of(flight)));

        if (maxStops == null || maxStops == 0) {
            return routes;
        }

        if (maxStops >= 1) {
            List<List<FlightEntity>> oneStopRoutes = findOneStopFlights(
                    origin, destination, date, passengers
            );
            routes.addAll(oneStopRoutes);
        }

        return routes;
    }

    private List<FlightEntity> findDirectFlights(
            AirportEntity origin,
            AirportEntity destination,
            LocalDate date,
            Integer passengers
    ) {
        List<FlightEntity> flights = flightRepository.getFlightsByFlightSearch(
                origin.getId(),
                date
        );

        return flights.stream()
                .filter(f -> f.getDestination().equals(destination))
                .filter(f -> hasAvailableSeats(f, passengers))
                .toList();
    }

    private List<List<FlightEntity>> findOneStopFlights(
            AirportEntity origin,
            AirportEntity destination,
            LocalDate date,
            Integer passengers
    ) {
        List<List<FlightEntity>> routes = new ArrayList<>();

        List<FlightEntity> firstLegFlights = flightRepository.getFlightsByFlightSearch(
                        origin.getId(),
                        date
                ).stream()
                .filter(f -> !f.getDestination().equals(destination))
                .filter(f -> hasAvailableSeats(f, passengers))
                .toList();

        for (FlightEntity firstFlight : firstLegFlights) {
            AirportEntity connectionAirport = firstFlight.getDestination();

            List<FlightEntity> secondLegFlights = findConnectionFlights(
                    connectionAirport,
                    destination,
                    LocalDateTime.of(firstFlight.getFlightDay(),firstFlight.getArrivalTime()),
                    passengers
            );

            for (FlightEntity secondFlight : secondLegFlights) {
                if (isValidConnection(firstFlight, secondFlight)) {
                    routes.add(List.of(firstFlight, secondFlight));
                }
            }
        }

        return routes;
    }


    private List<FlightEntity> findConnectionFlights(
            AirportEntity connectionAirport,
            AirportEntity finalDestination,
            LocalDateTime arrivalTime,
            Integer passengers
    ) {

        List<FlightEntity> sameDayFlights = flightRepository.getFlightsByFlightSearch(
                connectionAirport.getId(),
                arrivalTime.toLocalDate()
        );

        List<FlightEntity> nextDayFlights = flightRepository.getFlightsByFlightSearch(
                connectionAirport.getId(),
                arrivalTime.toLocalDate().plusDays(1)
        );

        return Stream.concat(sameDayFlights.stream(), nextDayFlights.stream())
                .filter(f -> f.getDestination().equals(finalDestination))
                .filter(f -> hasAvailableSeats(f, passengers))
                .filter(f -> LocalDateTime.of(f.getFlightDay(),f.getDepartureTime()).isAfter(arrivalTime.plusHours(1)))
                .filter(f -> LocalDateTime.of(f.getFlightDay(),f.getDepartureTime()).isBefore(arrivalTime.plusHours(24)))
                .toList();
    }

    private boolean isValidConnection(FlightEntity firstFlight, FlightEntity secondFlight) {
        Duration connectionTime = Duration.between(
                firstFlight.getArrivalTime(),
                secondFlight.getDepartureTime()
        );

        return connectionTime.toMinutes() >= 60 && connectionTime.toHours() <= 6;
    }

    private List<FlightPairDTO> createFlightPairs(
            List<List<FlightSummaryDTO>> outboundOptions,
            List<List<FlightSummaryDTO>> returnOptions
    ) {
        List<FlightPairDTO> pairs = new ArrayList<>();

        for (List<FlightSummaryDTO> outbound : outboundOptions) {
            for (List<FlightSummaryDTO> inbound : returnOptions) {
                FlightSummaryDTO outboundLast = outbound.get(outbound.size() - 1);
                FlightSummaryDTO inboundFirst = inbound.get(0);
                LocalDateTime lastOutboundArrival = LocalDateTime.of(outboundLast.getFlightDay(),outboundLast.getDepartureTime());
                LocalDateTime firstInboundDeparture = LocalDateTime.of(inboundFirst.getFlightDay(),inboundFirst.getDepartureTime());

                if (firstInboundDeparture.isAfter(lastOutboundArrival)) {
                    BigDecimal totalPrice = calculateTotalPrice(outbound, inbound);
                    int totalStops = (outbound.size() - 1) + (inbound.size() - 1);

                    pairs.add(new FlightPairDTO(outbound, inbound, totalStops, totalPrice));
                }
            }
        }
        return pairs.stream()
                .sorted(Comparator
                        .comparing(FlightPairDTO::getTotalStops)
                        .thenComparing(FlightPairDTO::getTotalPrice))
                .toList();
    }

    private BigDecimal calculateTotalPrice(
            List<FlightSummaryDTO> outbound,
            List<FlightSummaryDTO> inbound
    ) {
        BigDecimal outboundPrice = outbound.stream()
                .map(FlightSummaryDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal inboundPrice = inbound.stream()
                .map(FlightSummaryDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return outboundPrice.add(inboundPrice);
    }

    private boolean hasAvailableSeats(FlightEntity flight, Integer requiredSeats) {
        return flightHelper.getAvailableSeatsForFlight(flight.getId()).size() >= requiredSeats;
    }

    @Override
    public List<String> getAllOrigins() {
        return flightRepository.findAllOrigins();
    }

    @Override
    public List<String> getAllDestinations() {
        return flightRepository.findAllDestinations();
    }

    @Override
    public List<FlightDTO> transformListEntity(List<FlightEntity> flight) {
        return flightMapper.toDTOList(flight);
    }

    @Override
    public List<FlightEntity> transformSummaryDTO(List<FlightSummaryDTO> flights) {
        return flightMapper.toEntityList(flights);
    }
}
