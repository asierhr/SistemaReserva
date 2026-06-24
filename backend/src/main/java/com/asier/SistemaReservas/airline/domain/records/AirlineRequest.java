package com.asier.SistemaReservas.airline.domain.records;

import com.asier.SistemaReservas.airline.domain.dto.AirlineDTO;

public record AirlineRequest(AirlineDTO airlineDTO, String adminEmail) {
}
