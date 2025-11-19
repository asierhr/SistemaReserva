package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.AirportEntity;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.repositories.AirportRepository;
import com.asier.SistemaReservas.repositories.FlightRepository;
import com.asier.SistemaReservas.repositories.SeatRepository;
import com.asier.SistemaReservas.servicies.FlightHelper;
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


