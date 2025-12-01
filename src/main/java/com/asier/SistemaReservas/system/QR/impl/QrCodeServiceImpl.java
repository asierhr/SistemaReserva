package com.asier.SistemaReservas.system.QR.impl;

import com.asier.SistemaReservas.system.QR.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
public class QrCodeServiceImpl implements QRCodeService {
    @Override
    public String generateQRCodeBase64(String content, int width, int height) {
        try{
            byte[] qrBytes = generateQRCodeBytes(content, width, height);
            return Base64.getEncoder().encodeToString(qrBytes);
        } catch (Exception e) {
            log.error("Error generating QR code", e);
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }

    @Override
    public byte[] generateQRCodeBytes(String content, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }

    @Override
    public String generateReservationQRContent(Long reservationId, String userEmail, String flightNumber, String departureDate) {
        return String.format(
                "RESERVATION_ID:%d|EMAIL:%s|FLIGHT:%s|DATE:%s",
                reservationId, userEmail, flightNumber, departureDate
        );
    }

    @Override
    public String generateFlightQRContentJSON(Long reservationId, String userEmail, String airline, String departureDate, String seats) {
        return String.format("""
        {
            "type": "FLIGHT",
            "reservationId": %d,
            "email": "%s",
            "flight": "%s",
            "date": "%s",
            "seats": "%s"
        }
        """,
                reservationId, userEmail, airline, departureDate, seats
        );
    }

    @Override
    public String generateHotelQRContentJSON(Long reservationId, String userEmail, String hotelName, String checkInDate, String checkOutDate) {
        return String.format("""
        {
            "type": "HOTEL",
            "reservationId": %d,
            "email": "%s",
            "hotel": "%s",
            "checkIn": "%s",
            "checkOut": "%s"
        }
        """,
                reservationId, userEmail, hotelName, checkInDate, checkOutDate
        );
    }
}
