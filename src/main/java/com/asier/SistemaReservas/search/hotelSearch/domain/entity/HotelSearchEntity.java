package com.asier.SistemaReservas.search.hotelSearch.domain.entity;

import com.asier.SistemaReservas.search.domain.SearchEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotelSearch")
public class HotelSearchEntity extends SearchEntity {
    private String city;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guests;
}
