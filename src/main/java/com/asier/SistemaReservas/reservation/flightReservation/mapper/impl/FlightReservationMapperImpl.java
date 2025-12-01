package com.asier.SistemaReservas.reservation.flightReservation.mapper.impl;

import com.asier.SistemaReservas.flight.mapper.FlightMapper;
import com.asier.SistemaReservas.reservation.flightReservation.domain.DTO.FlightReservationDTO;
import com.asier.SistemaReservas.reservation.flightReservation.mapper.FlightReservationMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.seats.mapper.SeatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightReservationMapperImpl implements FlightReservationMapper {
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

    @Override
    public List<FlightReservationDTO> toDTOList(List<FlightReservationEntity> flightReservationEntities) {
        List<FlightReservationDTO> reservationDTOS = new ArrayList<>();
        for(FlightReservationEntity flightReservationEntity: flightReservationEntities){
            FlightReservationDTO flightReservationDTO =FlightReservationDTO.builder()
                    .id(flightReservationEntity.getId())
                    .reservationDate(flightReservationEntity.getReservationDate())
                    .bookingStatus(flightReservationEntity.getBookingStatus())
                    .totalPrice(flightReservationEntity.getTotalPrice())
                    .user(userMapper.toDTO(flightReservationEntity.getUser()))
                    .flight(flightMapper.toSummaryDTO(flightReservationEntity.getFlight()))
                    .seats(seatMapper.toDTOList(flightReservationEntity.getSeat()))
                    .build();
            reservationDTOS.add(flightReservationDTO);
        }
        return reservationDTOS;
    }
}
