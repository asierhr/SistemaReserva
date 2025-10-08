package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByHotelId(Long id);
}
