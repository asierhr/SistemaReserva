package com.asier.SistemaReservas.servicies;


import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;

import java.util.List;

public interface FlightReservationService {
    FlightReservationDTO createFlightReservation(Long id, List<Long> seatsId);
}
