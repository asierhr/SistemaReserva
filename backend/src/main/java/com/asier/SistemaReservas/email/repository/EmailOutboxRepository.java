package com.asier.SistemaReservas.email.repository;

import com.asier.SistemaReservas.email.domain.enums.OutboxStatus;
import com.asier.SistemaReservas.email.domain.entity.EmailOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailOutboxRepository extends JpaRepository<EmailOutboxEntity, Long> {
    @Query("SELECT e FROM EmailOutboxEntity e WHERE e.outboxStatus = :status AND (e.nextRetryAt IS NULL OR e.nextRetryAt <= :now)")
    List<EmailOutboxEntity> findPendingToProcess(@Param("status") OutboxStatus status, @Param("now")LocalDateTime now, Pageable pageRequest);
}
