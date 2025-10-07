package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.SeatDTO;
import com.asier.SistemaReservas.domain.entities.SeatEntity;

import java.util.List;

public interface SeatService {
    List<SeatDTO> createSeats(List<SeatDTO> seats);
    List<SeatDTO> getSeatsFromFlight(Long id);
    List<SeatEntity> getSeatFromIds(List<Long> seatIds);
    void updateListSeatsAvailability(List<SeatEntity> seats);
}
