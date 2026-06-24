package com.asier.SistemaReservas.flight.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FlightPairDTO {
    private List<FlightSummaryDTO> outbound;
    private List<FlightSummaryDTO> inbound;
    private Integer totalStops;
    private BigDecimal totalPrice;
}
