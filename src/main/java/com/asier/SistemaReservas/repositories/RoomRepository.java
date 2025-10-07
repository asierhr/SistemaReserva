package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
