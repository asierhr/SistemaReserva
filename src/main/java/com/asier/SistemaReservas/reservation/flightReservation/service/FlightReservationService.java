package com.asier.SistemaReservas.reservation.flightReservation.service;


import com.asier.SistemaReservas.reservation.flightReservation.domain.DTO.FlightReservationDTO;
import com.asier.SistemaReservas.reservation.flightReservation.domain.records.FlightReservationRequest;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;

import java.util.List;

public interface FlightReservationService {
    FlightReservationDTO createFlightReservation(Long id, FlightReservationRequest request);
    List<FlightReservationEntity> getFlightReservationsExpired();
    void updateFlightsReservations(List<FlightReservationEntity> flightReservations);
    List<FlightReservationDTO> getAllFlightReservations();
    FlightReservationEntity getFlightById(Long id);
    void updateFlightReservation(FlightReservationEntity flightReservation);
    List<FlightReservationEntity> getAllCheckInFalseAndFlightDone();
}
