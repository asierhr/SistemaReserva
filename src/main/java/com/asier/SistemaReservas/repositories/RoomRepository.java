package com.asier.SistemaReservas.repositories;

import com.asier.SistemaReservas.domain.entities.RoomEntity;
import com.asier.SistemaReservas.domain.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByHotelId(Long id);

    @Query("""
    SELECT DISTINCT r
    FROM RoomEntity r
    JOIN r.hotel h
    WHERE h.location.city = :city
      AND r.type IN :roomTypes
      AND r.costPerNight BETWEEN :minPrice AND :maxPrice
      AND NOT EXISTS (
          SELECT 1
          FROM RoomReservationEntity rr
          JOIN rr.reservation hr
          WHERE rr.room = r
            AND hr.bookingStatus NOT IN ('CANCELLED', 'REFUNDED')
            AND hr.checkIn < :checkOut
            AND hr.checkOut > :checkIn
      )
""")
    List<RoomEntity> findRoomsNearAverage(
            @Param("city") String city,
            @Param("roomTypes") List<RoomType> roomTypes,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );


    @Query("""
    SELECT AVG(r.costPerNight)
    FROM RoomEntity r
    JOIN r.hotel h
    WHERE h.location.city = :city
    AND r.type IN :roomTypes
    """)
    Double findAveragePriceByCityAndRoomType(@Param("city") String city, @Param("roomTypes") List<RoomType> roomTypes);

    @Query(value = """
    SELECT STDDEV(r.cost_per_night)
    FROM rooms r
    JOIN hotels h ON r.hotel_id = h.id
    WHERE h.city = :city
    AND r.type IN :roomTypes
    """, nativeQuery = true)
    Double findPriceStdDevByCity(@Param("city") String city, @Param("roomTypes") List<RoomType> roomTypes);
}
