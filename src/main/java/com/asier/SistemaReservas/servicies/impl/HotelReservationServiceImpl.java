package com.asier.SistemaReservas.servicies.impl;

import com.asier.SistemaReservas.domain.dto.HotelReservationDTO;
import com.asier.SistemaReservas.domain.dto.RoomDTO;
import com.asier.SistemaReservas.domain.entities.HotelReservationEntity;
import com.asier.SistemaReservas.domain.entities.RoomEntity;
import com.asier.SistemaReservas.domain.enums.BookingStatus;
import com.asier.SistemaReservas.mapper.HotelReservationMapper;
import com.asier.SistemaReservas.repositories.HotelReservationRepository;
import com.asier.SistemaReservas.servicies.HotelReservationService;
import com.asier.SistemaReservas.servicies.HotelService;
import com.asier.SistemaReservas.servicies.RoomService;
import com.asier.SistemaReservas.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelReservationServiceImpl implements HotelReservationService {
    private final HotelReservationRepository hotelReservationRepository;
    private final HotelReservationMapper hotelReservationMapper;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final UserService user;

    private BigDecimal validateRooms(Long id, List<RoomEntity> rooms){
        if(rooms.isEmpty())  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No rooms provided");
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(RoomEntity room: rooms){
            if(!id.equals(room.getHotel().getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Seat " + room.getId() + " does not belong to flight " + id);
            if(!room.isAvailable()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room not available");
            totalPrice = totalPrice.add(room.getCostPerNight());
        }
        return totalPrice;
    }

    @Override
    public HotelReservationDTO createReservation(Long id, List<Long> roomIds) {
        if(!hotelService.existsHotel(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        List<RoomEntity> rooms = roomService.getRoomsFromIds(roomIds);
        HotelReservationEntity hotel = HotelReservationEntity.builder()
                .reservationDate(LocalDateTime.now())
                .totalPrice(validateRooms(id, rooms))
                .bookingStatus(BookingStatus.PENDING_PAYMENT)
                .user(user.getUserEntity())
                .hotel(hotelService.getHotelEntity(id))
                .room(rooms)
                .build();
        for(RoomEntity room : rooms){
            room.setAvailable(false);
            room.setReservation(hotel);
        }
        HotelReservationEntity savedReservation = hotelReservationRepository.save(hotel);
        return hotelReservationMapper.toDTO(savedReservation);
    }
}
