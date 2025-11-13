package com.asier.SistemaReservas.domain.records;

import java.time.LocalDate;

public record HotelSearch(String city, LocalDate checkIn, LocalDate checkOut, Integer guests){
}
