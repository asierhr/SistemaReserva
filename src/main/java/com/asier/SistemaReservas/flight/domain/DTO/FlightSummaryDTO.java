package com.asier.SistemaReservas.flight.domain.DTO;

import com.asier.SistemaReservas.aiport.domain.DTO.AirportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSummaryDTO {
    private Long id;
    private AirportDTO origin;
    private AirportDTO destination;
    private String airline;
    private LocalDate flightDay;
}
