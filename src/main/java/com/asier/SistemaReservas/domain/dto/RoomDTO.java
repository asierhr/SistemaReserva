package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.entities.HotelEntity;
import com.asier.SistemaReservas.domain.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {
    private Long id;
    private Integer hotelId;
    private String numRoom;
    private RoomType type;
    private BigDecimal costPerNight;
    private boolean available;
}
