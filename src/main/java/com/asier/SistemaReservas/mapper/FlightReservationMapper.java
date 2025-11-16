package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.domain.dto.FlightSummaryDTO;
import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import org.mapstruct.Mapper;

public interface FlightReservationMapper {
    FlightReservationDTO toDTO(FlightReservationEntity flightReservationEntity);
    FlightReservationEntity toEntity(FlightReservationDTO flightReservationDTO);
}
