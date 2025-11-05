package com.asier.SistemaReservas.domain.dto;

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
    private String origin;
    private String destination;
    private String airline;
    private LocalDate flightDay;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private List<SeatDTO> seats;
}
