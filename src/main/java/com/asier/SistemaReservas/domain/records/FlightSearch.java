package com.asier.SistemaReservas.domain.records;

import java.time.LocalDate;

public record FlightSearch(String origin, String destination, LocalDate departureDay, LocalDate returnDay, Integer passengers) {
}
