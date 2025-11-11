package com.asier.SistemaReservas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightPairDTO {
    private FlightSummaryDTO outbound;
    private FlightSummaryDTO inbound;
}
