package com.asier.SistemaReservas.seats.service;

import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import com.asier.SistemaReservas.seats.domain.entity.SeatEntity;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;

import java.util.List;
import java.util.Map;

public interface SeatService {
    List<SeatDTO> createSeats(List<SeatDTO> seats);
    Map<SeatClass,List<SeatDTO>> getSeatsFromFlight(Long id);
    List<SeatEntity> getSeatFromIds(List<Long> seatIds);
    void updateListSeatsAvailability(List<SeatEntity> seats);
    List<SeatEntity> getAvailableSeats(Long id);
    SeatEntity getSeatFromId(Long id);
}
