package com.asier.SistemaReservas.reservation.hotelReservation.domain.records;

import java.time.LocalDate;
import java.util.List;

public record ReservationHotelRequest(
        List<Long> roomIds,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
