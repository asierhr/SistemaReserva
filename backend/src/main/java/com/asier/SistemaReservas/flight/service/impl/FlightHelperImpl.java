package com.asier.SistemaReservas.flight.service.impl;

import com.asier.SistemaReservas.aiport.domain.entity.AirportEntity;
import com.asier.SistemaReservas.flight.repository.FlightRepository;
import com.asier.SistemaReservas.flight.service.FlightHelper;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.aiport.repository.AirportRepository;
import com.asier.SistemaReservas.seats.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightHelperImpl implements FlightHelper {
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Override
    public List<SeatEntity> getAvailableSeatsForFlight(Long flightId) {
        return seatRepository.findAllByFlightIdAndAvailableTrue(flightId);
    }

    @Override
    public boolean flightExists(Long flightId) {
        return flightRepository.existsById(flightId);
    }

    @Override
    public AirportEntity getAirport(Long id) {
        return airportRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public AirportEntity getAirportByLocationCity(String city) {
        return airportRepository.findByLocationCity(city);
    }
}


