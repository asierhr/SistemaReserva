package com.asier.SistemaReservas.flight.domain.DTO;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSummaryDTO {
    private Long id;
    private String flightNumber;
    private AirportDTO origin;
    private AirportDTO destination;
    private AirlineDTO airlineDTO;
    private LocalDate flightDay;
    private BigDecimal price;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
}
