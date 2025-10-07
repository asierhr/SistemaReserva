package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.entities.UserEntity;
import com.asier.SistemaReservas.domain.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private BookingStatus bookingStatus;
    private UserDTO user;
}
