package com.asier.SistemaReservas.domain.dto;

import com.asier.SistemaReservas.domain.entities.ReservationEntity;
import com.asier.SistemaReservas.domain.enums.BookingStatus;
import com.asier.SistemaReservas.domain.enums.PaymentMethod;
import com.asier.SistemaReservas.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Long paymentId;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private ReservationEntity reservation;
    private BigDecimal amount;
}
