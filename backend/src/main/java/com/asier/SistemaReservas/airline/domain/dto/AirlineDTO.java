package com.asier.SistemaReservas.airline.domain.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AirlineDTO {
    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String country;
    private LocalDateTime createdAt;
}
