package com.asier.SistemaReservas.search.hotelSearch.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HotelSearchDTO {
    private String city;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guests;
}
