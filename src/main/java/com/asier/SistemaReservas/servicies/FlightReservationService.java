package com.asier.SistemaReservas.servicies;


import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import com.asier.SistemaReservas.domain.enums.SeatClass;
import com.asier.SistemaReservas.domain.records.FlightReservationRequest;
import com.asier.SistemaReservas.domain.records.FlightSearch;

import java.util.List;

public interface FlightReservationService {
    FlightReservationDTO createFlightReservation(Long id, FlightReservationRequest request);
    List<FlightReservationEntity> getFlightReservationsExpired();
    void updateFlightsReservations(List<FlightReservationEntity> flightReservations);
    List<FlightReservationDTO> getAllFlightReservations();
}
