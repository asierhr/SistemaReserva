package com.asier.SistemaReservas.search.flightSearch.domain.entity;

import com.asier.SistemaReservas.search.domain.SearchEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flightSearch")
public class FlightSearchEntity extends SearchEntity {
    private String origin;
    private String destination;
    private LocalDate departureDay;
    private LocalDate returnDay;
    private Integer passengers;
    private Integer maxStops;
}
