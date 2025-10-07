package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.FlightReservationDTO;
import com.asier.SistemaReservas.domain.entities.FlightReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FlightReservationMapperImpl implements FlightReservationMapper{
    private final SeatMapper seatMapper;
    private final FlightMapper flightMapper;
    private final UserMapper userMapper;

    @Override
    public FlightReservationDTO toDTO(FlightReservationEntity flightReservationEntity) {
        return FlightReservationDTO.builder()
                .id(flightReservationEntity.getId())
                .reservationDate(flightReservationEntity.getReservationDate())
                .bookingStatus(flightReservationEntity.getBookingStatus())
                .totalPrice(flightReservationEntity.getTotalPrice())
                .user(userMapper.toDTO(flightReservationEntity.getUser()))
                .flight(flightMapper.toSummaryDTO(flightReservationEntity.getFlight()))
                .seats(seatMapper.toDTOList(flightReservationEntity.getSeat()))
                .build();
    }

    @Override
    public FlightReservationEntity toEntity(FlightReservationDTO flightReservationDTO) {
        return FlightReservationEntity.builder()
                .reservationDate(flightReservationDTO.getReservationDate())
                .bookingStatus(flightReservationDTO.getBookingStatus())
                .totalPrice(flightReservationDTO.getTotalPrice())
                .user(userMapper.toEntity(flightReservationDTO.getUser()))
                .flight(flightMapper.toEntity(flightReservationDTO.getFlight()))
                .seat(seatMapper.toEntityList(flightReservationDTO.getSeats()))
                .build();
    }
}
