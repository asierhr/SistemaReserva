package com.asier.SistemaReservas.seats.domain.DTO;

import com.asier.SistemaReservas.seats.domain.enums.SeatType;
import com.asier.SistemaReservas.seats.domain.enums.SeatClass;
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
