package com.asier.SistemaReservas.flight.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightPairDTO {
    private FlightSummaryDTO outbound;
    private FlightSummaryDTO inbound;
}
