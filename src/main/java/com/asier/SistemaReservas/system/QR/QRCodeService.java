package com.asier.SistemaReservas.system.QR;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface QRCodeService {
    String generateQRCodeBase64(String content, int width, int height);
    byte[] generateQRCodeBytes(String content, int width, int height) throws WriterException, IOException;
    String generateFlightQRContentJSON(Long reservationId, String userEmail, String flightNumber, String departureDate, String seats);
    String generateHotelQRContentJSON(Long reservationId, String userEmail, String hotelName, String checkInDate, String checkOutDate);
    String generateReservationQRContentJSON(ReservationEntity reservation);
}
