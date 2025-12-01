package com.asier.SistemaReservas.reservation.hotelReservation.domain.DTO;

import com.asier.SistemaReservas.hotel.domain.DTO.HotelSummaryDTO;
import com.asier.SistemaReservas.reservation.domain.DTO.ReservationDTO;
import com.asier.SistemaReservas.room.domain.DTO.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HotelReservationDTO extends ReservationDTO {
    private List<RoomDTO> rooms;
    private HotelSummaryDTO hotel;
}
