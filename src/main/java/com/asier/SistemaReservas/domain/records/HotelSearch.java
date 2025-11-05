package com.asier.SistemaReservas.domain.records;

import java.time.LocalDate;

public record HotelSearch(String destination, LocalDate checkIn, LocalDate checkOut){
}
