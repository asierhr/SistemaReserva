package com.asier.SistemaReservas.domain.entities;

import com.asier.SistemaReservas.domain.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "reservation")
    private List<PaymentEntity> payments;
}
