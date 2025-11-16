package com.asier.SistemaReservas.domain.entities;

import com.asier.SistemaReservas.domain.enums.BookingStatus;
import com.asier.SistemaReservas.domain.enums.PaymentMethod;
import com.asier.SistemaReservas.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String stripePaymentIntentId;

    @Column(unique = true)
    private String stripeChargeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(length = 1000)
    private String stripeCustomerId;

    private PaymentMethod paymentMethod;

    @Column(length = 4)
    private String cardLast4;

    @Column(length = 50)
    private String cardBrand;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    private LocalDateTime failedAt;

    private LocalDateTime webhookReceivedAt;

    @Column(length = 500)
    private String failureMessage;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
