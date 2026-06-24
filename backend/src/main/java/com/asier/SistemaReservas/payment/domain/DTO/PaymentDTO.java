package com.asier.SistemaReservas.payment.domain.DTO;

import com.asier.SistemaReservas.payment.domain.enums.PaymentMethod;
import com.asier.SistemaReservas.payment.domain.enums.PaymentStatus;
import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
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
