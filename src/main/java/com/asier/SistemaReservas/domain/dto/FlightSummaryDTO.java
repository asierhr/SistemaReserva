package com.asier.SistemaReservas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSummaryDTO {
    private Long id;
    private String origin;
    private String destination;
    private String airline;
    private LocalDate flightDay;
}
