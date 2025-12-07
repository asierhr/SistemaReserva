package com.asier.SistemaReservas.system.QR.impl;

import com.asier.SistemaReservas.reservation.domain.entity.ReservationEntity;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.flightReservation.service.FlightReservationService;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationService;
import com.asier.SistemaReservas.system.QR.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QRCodeService {
    private final FlightReservationService flightReservationService;
    private final HotelReservationService hotelReservationService;

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

    @Override
    public String generateReservationQRContentJSON(ReservationEntity reservation) {
        String qrCodeBase64 = "";
        if (reservation instanceof FlightReservationEntity) {
            FlightReservationEntity flightReservation = flightReservationService.getFlightById(reservation.getId());
            String qrContent = generateFlightQRContentJSON(
                    flightReservation.getId(),
                    flightReservation.getUser().getMail(),
                    flightReservation.getFlight().getAirline(),
                    flightReservation.getFlight().getDepartureTime().toString(),
                    flightReservation.getSeat().stream()
                            .map(s -> s.getSeatNumber())
                            .collect(Collectors.joining(", "))
            );

            log.info("QR Content: {}", qrContent);

            qrCodeBase64 = generateQRCodeBase64(qrContent, 300, 300);

            log.info("Reservation sent to Kafka: {}", flightReservation.getId());

        } else if (reservation instanceof HotelReservationEntity) {
            HotelReservationEntity hotelReservation = hotelReservationService.getReservationById(reservation.getId());
            String qrContent = generateHotelQRContentJSON(
                    hotelReservation.getId(),
                    hotelReservation.getUser().getMail(),
                    hotelReservation.getHotel().getHotelName(),
                    hotelReservation.getCheckIn().toString(),
                    hotelReservation.getCheckOut().toString()
            );

            log.info("QR Content: {}", qrContent);

            System.out.println("Hola");

            qrCodeBase64 = generateQRCodeBase64(qrContent, 300, 300);

            System.out.println("Hola");

            log.info("Reservation sent to Kafka: {}", hotelReservation.getId());
        }
        return qrCodeBase64;
    }
}
