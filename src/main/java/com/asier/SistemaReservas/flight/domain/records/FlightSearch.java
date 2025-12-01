package com.asier.SistemaReservas.flight.domain.records;

import java.time.LocalDate;

public record FlightSearch(String origin, String destination, LocalDate departureDay, LocalDate returnDay, Integer passengers) {
}
