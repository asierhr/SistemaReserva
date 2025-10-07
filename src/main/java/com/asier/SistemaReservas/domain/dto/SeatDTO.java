package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.enums.SeatClass;
import com.asier.SistemaReservas.domain.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long id;
    private String seatNumber;
    private Integer seatRow;
    private String seatColumn;
    private SeatClass seatClass;
    private SeatType seatType;
    private BigDecimal costPerSeat;
    private Boolean available = true;
    private Integer flightId;
}
