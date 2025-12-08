package com.asier.SistemaReservas.reservation.domain.entity;

import com.asier.SistemaReservas.payment.domain.entity.PaymentEntity;
import com.asier.SistemaReservas.reservation.domain.enums.BookingStatus;
import com.asier.SistemaReservas.user.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "reservations")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationDate;

    private BigDecimal totalPriceAfterDiscount;

    private BigDecimal refundedAmount;

    private BigDecimal totalPrice;

    @Column(name = "checked_in")
    private Boolean checkedIn = false;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private LocalDateTime cancellationDeadline;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "reservation")
    private List<PaymentEntity> payments;


}
