package com.asier.SistemaReservas.flight.domain.DTO;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import com.asier.SistemaReservas.seats.domain.DTO.SeatDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private Long id;
    private String flightNumber;
    private AirportDTO origin;
    private AirportDTO destination;
    private AirlineDTO airlineDTO;
    private LocalDate flightDay;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private List<SeatDTO> seats;
}
