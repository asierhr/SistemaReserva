package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.entities.SeatEntity;
import com.asier.SistemaReservas.domain.enums.SeatClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime flightDay;
    private List<SeatDTO> seats;
}
