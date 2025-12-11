package com.asier.SistemaReservas.hotel.service;


import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.hotel.domain.DTO.HotelDTO;
import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO.HotelReservationDTO;

import java.math.BigDecimal;
import java.util.List;

public interface HotelService {
    HotelDTO createHotel(HotelDTO hotel);
    HotelSummaryDTO getHotel(Long id);
    HotelEntity getHotelEntity(Long id);
    List<HotelEntity> getAllHotels();
    boolean existsHotel(Long id);
    Double updateRating(HotelEntity hotel);
    Integer occupiedRooms(HotelEntity hotel);
    List<HotelReservationDTO> getCheckIns(HotelEntity hotel);
    List<HotelReservationDTO> getCheckOuts(HotelEntity hotel);
    List<HotelReservationDTO> getCancellations(HotelEntity hotel);
    List<HotelReservationDTO> getBookingsFromToday(HotelEntity hotel);
    Integer totalCheckIns(HotelEntity hotel);
    Integer totalCheckOuts(HotelEntity hotel);
    Integer totalCancellations(HotelEntity hotel);
    Integer totalBookings(HotelEntity hotel);
    BigDecimal dailyRevenue(HotelEntity hotel);
}
