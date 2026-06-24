package com.asier.SistemaReservas.reservation.flightReservation.mapper;

import com.asier.SistemaReservas.reservation.flightReservation.domain.DTO.FlightReservationDTO;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;

import java.util.List;

public interface FlightReservationMapper {
    FlightReservationDTO toDTO(FlightReservationEntity flightReservationEntity);
    FlightReservationEntity toEntity(FlightReservationDTO flightReservationDTO);
    List<FlightReservationDTO> toDTOList(List<FlightReservationEntity> flightReservationEntities);
}
