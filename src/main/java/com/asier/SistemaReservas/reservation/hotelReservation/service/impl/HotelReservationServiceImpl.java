package com.asier.SistemaReservas.reservation.hotelReservation.service.impl;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.repository.HotelReservationRepository;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationService;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import com.asier.SistemaReservas.room.domain.entity.RoomReservationEntity;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.reservation.hotelReservation.mapper.HotelReservationMapper;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.room.service.RoomService;
import com.asier.SistemaReservas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReservationServiceImpl implements HotelReservationService {
    private final HotelReservationRepository hotelReservationRepository;
    private final HotelReservationMapper hotelReservationMapper;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final UserService userService;

    private BigDecimal validateRooms(Long id, List<RoomEntity> rooms){
        if(rooms.isEmpty())  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No rooms provided");
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(RoomEntity room: rooms){
            if(!id.equals(room.getHotel().getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Room " + room.getId() + " does not belong to hotel " + id);
            totalPrice = totalPrice.add(room.getCostPerNight());
        }
        return totalPrice;
    }

    @Override
    public HotelReservationDTO createReservation(Long id, List<Long> roomIds, LocalDate checkIn, LocalDate checkOut) {
        if(!hotelService.existsHotel(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<RoomEntity> rooms = roomService.getRoomsFromIds(roomIds);

        HotelReservationEntity hotel = HotelReservationEntity.builder()
                .reservationDate(LocalDateTime.now())
                .totalPrice(validateRooms(id, rooms))
                .bookingStatus(BookingStatus.PENDING_PAYMENT)
                .user(userService.getUserEntity())
                .hotel(hotelService.getHotelEntity(id))
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();

        List<RoomReservationEntity> roomReservations = new ArrayList<>();

        for (RoomEntity room : rooms) {
            RoomReservationEntity rr = RoomReservationEntity.builder()
                    .room(room)
                    .reservation(hotel)
                    .build();

            roomReservations.add(rr);

            room.setAvailable(false);
        }
        hotel.setRooms(roomReservations);

        HotelReservationEntity savedReservation = hotelReservationRepository.save(hotel);
        return hotelReservationMapper.toDTO(savedReservation);
    }

    @Override
    public List<HotelReservationDTO> getUserReservations() {
        UserEntity user = userService.getUserEntity();
        return hotelReservationMapper.toDTOList(hotelReservationRepository.findAllByUserId(user.getId()));
    }
}
