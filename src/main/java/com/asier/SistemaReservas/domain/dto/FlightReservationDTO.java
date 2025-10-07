package com.asier.SistemaReservas.domain.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservationDTO extends ReservationDTO{
    private List<SeatDTO> seats;
    private FlightSummaryDTO flight;
}
