package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;
import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;
import com.asier.SistemaReservas.domain.entities.RoomReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelReservationMapperImpl implements HotelReservationMapper{
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
}
