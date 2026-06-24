package com.asier.SistemaReservas.room.domain.DTO;

import com.asier.SistemaReservas.room.domain.enums.RoomType;
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
