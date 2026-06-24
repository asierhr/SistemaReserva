package com.asier.SistemaReservas.reservation.hotelReservation.repository;

import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservationEntity, Long> {
    List<HotelReservationEntity> findAllByUserId(Long id);
}
