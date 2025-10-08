package com.asier.SistemaReservas.mapper;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;
import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                .rooms(roomMapper.toDTOList(hotelReservation.getRoom()))
                .build();
    }

    @Override
    public HotelReservationEntity toEntity(HotelReservationDTO hotelReservation) {
        return HotelReservationEntity.builder()
                .id(hotelReservation.getId())
                .totalPrice(hotelReservation.getTotalPrice())
                .bookingStatus(hotelReservation.getBookingStatus())
                .reservationDate(hotelReservation.getReservationDate())
                .user(userMapper.toEntity(hotelReservation.getUser()))
                .hotel(hotelMapper.toEntity(hotelReservation.getHotel()))
                .room(roomMapper.toEntityList(hotelReservation.getRooms()))
                .build();
    }
}
