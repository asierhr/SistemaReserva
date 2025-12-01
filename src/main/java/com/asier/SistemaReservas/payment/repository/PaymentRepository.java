package com.asier.SistemaReservas.payment.repository;

import com.asier.SistemaReservas.payment.domain.entity.PaymentEntity;
import com.asier.SistemaReservas.payment.domain.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByReservationIdAndStatus(Long id, PaymentStatus status);
    Optional<PaymentEntity> findByStripePaymentIntentId(String id);
    Optional<PaymentEntity> findByStripeChargeId(String stripeChargeId);
    @Query("SELECT p FROM PaymentEntity p WHERE p.reservation.id = :reservationId " +
            "AND p.status = 'SUCCEEDED' ORDER BY p.paidAt DESC")
    Optional<PaymentEntity> findSuccessfulPaymentByReservation(@Param("reservationId") Long reservationId);
}
