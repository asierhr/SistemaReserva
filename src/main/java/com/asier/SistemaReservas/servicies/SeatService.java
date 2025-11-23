package com.asier.SistemaReservas.servicies;

import com.asier.SistemaReservas.domain.dto.SeatDTO;
import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.domain.enums.SeatClass;

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
