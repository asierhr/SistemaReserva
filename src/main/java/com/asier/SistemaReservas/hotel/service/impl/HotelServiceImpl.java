package com.asier.SistemaReservas.hotel.service.impl;

import com.asier.SistemaReservas.comment.domain.DTO.CommentDTO;
import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import com.asier.SistemaReservas.comment.service.CommentHelper;
import com.asier.SistemaReservas.hotel.history.service.HotelHistoryService;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelDTO;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.mapper.HotelMapper;
import com.asier.SistemaReservas.hotel.repository.HotelRepository;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.reservation.domain.DTO.ReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationHelper;
import com.asier.SistemaReservas.room.domain.entity.RoomEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HotelHistoryService hotelHistoryService;
    private final CommentHelper commentHelper;
    private final HotelReservationHelper hotelReservationHelper;


    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        HotelEntity hotel = hotelMapper.toEntity(hotelDTO);
        hotel.getRooms().forEach(roomEntity -> {
            roomEntity.setHotel(hotel);
            roomEntity.setAvailable(true);
        });
        HotelEntity savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDTO(savedHotel);
    }

    @Override
    public HotelSummaryDTO getHotel(Long id) {
        HotelEntity hotel = getHotelEntity(id);
        if(!hotelHistoryService.existsHotelHistory()) hotelHistoryService.createHotelHistory();
        hotelHistoryService.updateHotelHistory(hotel);
        return hotelMapper.toSummaryDTO(hotel);
    }

    @Override
    public HotelEntity getHotelEntity(Long id) {
        return hotelRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    }

    @Override
    public List<HotelEntity> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public boolean existsHotel(Long id) {
        return hotelRepository.existsById(id);
    }

    @Override
    public Double updateRating(HotelEntity hotel) {
        List<CommentEntity> comments = hotel.getComments();
        Double rating = 0.0;
        for(CommentEntity comment: comments) rating += comment.getRating();
        hotel.setRating(rating/comments.size());
        hotelRepository.save(hotel);
        return hotel.getRating();
    }

    @Override
    public List<HotelReservationDTO> getCheckIns(HotelEntity hotel) {
        List<HotelReservationEntity> hotelReservations = hotel.getHotelReservations().stream()
                .filter(reservation -> reservation.getCheckIn().equals(LocalDate.now()))
                .toList();
        return hotelReservationHelper.transformToDTOList(hotelReservations);
    }

    @Override
    public List<HotelReservationDTO> getCheckOuts(HotelEntity hotel) {
        List<HotelReservationEntity> hotelReservations = hotel.getHotelReservations().stream()
                .filter(reservation -> reservation.getCheckOut().equals(LocalDate.now()))
                .toList();
        return hotelReservationHelper.transformToDTOList(hotelReservations);
    }

    @Override
    public List<HotelReservationDTO> getCancellations(HotelEntity hotel) {
        List<HotelReservationEntity> hotelReservations = hotel.getHotelReservations().stream()
                .filter(reservation -> reservation.getCancelledAt().toLocalDate().equals(LocalDate.now()))
                .toList();
        return hotelReservationHelper.transformToDTOList(hotelReservations);
    }

    @Override
    public List<HotelReservationDTO> getBookingsFromToday(HotelEntity hotel) {
        List<HotelReservationEntity> hotelReservation = hotel.getHotelReservations().stream()
                .filter(reservation -> reservation.getReservationDate().toLocalDate().equals(LocalDate.now()))
                .toList();
        return hotelReservationHelper.transformToDTOList(hotelReservation);
    }

    @Override
    public Integer occupiedRooms(HotelEntity hotel) {
        List<RoomEntity> rooms = hotel.getRooms().stream()
                .filter(room -> !room.isAvailable())
                .toList();
        return rooms.size();
    }

    @Override
    public Integer totalCheckIns(HotelEntity hotel) {
        return getCheckIns(hotel).size();
    }

    @Override
    public Integer totalCheckOuts(HotelEntity hotel) {
        return getCheckOuts(hotel).size();
    }

    @Override
    public Integer totalCancellations(HotelEntity hotel) {
        return getCancellations(hotel).size();
    }

    @Override
    public Integer totalBookings(HotelEntity hotel) {
        return getBookingsFromToday(hotel).size();
    }

    @Override
    public BigDecimal dailyRevenue(HotelEntity hotel) {
        BigDecimal totalRevenue = getBookingsFromToday(hotel).stream()
                .map(ReservationDTO::getTotalPrice)               // Stream<BigDecimal>
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalRevenue;
    }
}
