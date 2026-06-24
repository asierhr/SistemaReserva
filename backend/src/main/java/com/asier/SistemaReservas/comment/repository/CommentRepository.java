package com.asier.SistemaReservas.comment.repository;

import com.asier.SistemaReservas.comment.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByHotelId(Long hotelId);
}
