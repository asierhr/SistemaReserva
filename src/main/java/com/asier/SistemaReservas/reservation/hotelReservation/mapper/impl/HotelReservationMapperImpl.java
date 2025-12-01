package com.asier.SistemaReservas.reservation.hotelReservation.mapper.impl;

import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.mapper.HotelReservationMapper;
import com.asier.SistemaReservas.user.mapper.UserMapper;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.room.domain.entity.RoomReservationEntity;
import com.asier.SistemaReservas.room.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelReservationMapperImpl implements HotelReservationMapper {
    private final HotelMapper hotelMapper;
    private final RoomMapper roomMapper;
    private final UserMapper userMapper;

    @Override
    public HotelReservationDTO toDTO(HotelReservationEntity hotelReservation) {
        return HotelReservationDTO.builder()
                .id(hotelReservation.getId())
                .totalPrice(hotelReservation.getTotalPrice())
                .bookingStatus(hotelReservation.getBookingStatus())
                .reservationDate(hotelReservation.getReservationDate())
                .user(userMapper.toDTO(hotelReservation.getUser()))
                .hotel(hotelMapper.toSummaryDTO(hotelReservation.getHotel()))
                .rooms(hotelReservation.getRooms().stream()
                        .map(rr -> roomMapper.toDTO(rr.getRoom()))
                        .toList())
                .build();
    }

    @Override
    public HotelReservationEntity toEntity(HotelReservationDTO hotelReservation) {
        HotelReservationEntity reservation = HotelReservationEntity.builder()
                .id(hotelReservation.getId())
                .totalPrice(hotelReservation.getTotalPrice())
                .bookingStatus(hotelReservation.getBookingStatus())
                .reservationDate(hotelReservation.getReservationDate())
                .user(userMapper.toEntity(hotelReservation.getUser()))
                .hotel(hotelMapper.toEntity(hotelReservation.getHotel()))
                .build();

        List<RoomReservationEntity> roomReservations = hotelReservation.getRooms().stream()
                .map(roomDTO -> {
                    RoomReservationEntity rr = new RoomReservationEntity();
                    rr.setReservation(reservation);
                    rr.setRoom(roomMapper.toEntity(roomDTO));
                    return rr;
                })
                .toList();

        reservation.setRooms(roomReservations);

        return reservation;
    }

    @Override
    public List<HotelReservationDTO> toDTOList(List<HotelReservationEntity> hotelReservation) {
        List<HotelReservationDTO> hotelReservationDTOS = new ArrayList<>();
        for(HotelReservationEntity hotel: hotelReservation){
            HotelReservationDTO hotelReservationDTO = HotelReservationDTO.builder()
                    .id(hotel.getId())
                    .totalPrice(hotel.getTotalPrice())
                    .bookingStatus(hotel.getBookingStatus())
                    .reservationDate(hotel.getReservationDate())
                    .user(userMapper.toDTO(hotel.getUser()))
                    .hotel(hotelMapper.toSummaryDTO(hotel.getHotel()))
                    .rooms(hotel.getRooms().stream()
                            .map(rr -> roomMapper.toDTO(rr.getRoom()))
                            .toList())
                    .build();
            hotelReservationDTOS.add(hotelReservationDTO);
        }
        return hotelReservationDTOS;
    }
}
