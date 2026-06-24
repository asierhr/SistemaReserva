package com.asier.SistemaReservas.reservation.flightReservation.domain.DTO;

import com.asier.SistemaReservas.flight.domain.DTO.FlightSummaryDTO;
import com.asier.SistemaReservas.reservation.domain.DTO.ReservationDTO;
import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservationDTO extends ReservationDTO {
    private List<SeatDTO> seats;
    private FlightSummaryDTO flight;
    private String qrCode;
}
