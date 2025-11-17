package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.repositories.FlightRepository;
import com.asier.SistemaReservas.repositories.SeatRepository;
import com.asier.SistemaReservas.servicies.FlightSeatHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSeatHelperImpl implements FlightSeatHelper {
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    public List<SeatEntity> getAvailableSeatsForFlight(Long flightId) {
        return seatRepository.findAllByFlightIdAndAvailableTrue(flightId);
    }

    public boolean flightExists(Long flightId) {
        return flightRepository.existsById(flightId);
    }
}


