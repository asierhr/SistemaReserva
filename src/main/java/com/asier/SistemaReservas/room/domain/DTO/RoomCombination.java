package com.asier.SistemaReservas.room.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomCombination {
    private Long hotelId;
    private List<RoomDTO> rooms;
    private BigDecimal totalPrice;
}
