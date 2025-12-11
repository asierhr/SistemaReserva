package com.asier.SistemaReservas.reservation.domain.DTO;

import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.user.domain.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ReservationDTO {
    private Long id;
    private LocalDateTime reservationDate;
    private BigDecimal totalPrice;
    private Integer totalGuests;
    private BookingStatus bookingStatus;
    private UserDTO user;
}
