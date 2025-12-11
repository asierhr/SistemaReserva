package com.asier.SistemaReservas.search.flightSearch.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FlightSearchDTO {
    private String origin;
    private String destination;
    private LocalDate departureDay;
    private LocalDate returnDay;
    private Integer passengers;
    private Integer maxStops;
}
