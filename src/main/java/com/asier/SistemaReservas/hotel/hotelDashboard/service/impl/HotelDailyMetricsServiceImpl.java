package com.asier.SistemaReservas.hotel.hotelDashboard.service.impl;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO.HotelDailyMetricsDTO;
import com.asier.SistemaReservas.hotel.hotelDashboard.domain.DTO.HotelDashboardDTO;
import com.asier.SistemaReservas.hotel.hotelDashboard.domain.entity.HotelDailyMetricsEntity;
import com.asier.SistemaReservas.hotel.hotelDashboard.mapper.HotelDailyMetricsMapper;
import com.asier.SistemaReservas.hotel.hotelDashboard.repository.HotelDailyMetricsRepository;
import com.asier.SistemaReservas.hotel.hotelDashboard.service.HotelDailyMetricsService;
import com.asier.SistemaReservas.hotel.service.HotelService;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.service.HotelReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelDailyMetricsServiceImpl implements HotelDailyMetricsService {
    private final HotelDailyMetricsRepository hotelDailyMetricsRepository;
    private final HotelService hotelService;
    private final HotelReservationService hotelReservationService;
    private final HotelDailyMetricsMapper hotelDailyMetricsMapper;
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void createDailyMetric(Long hotelId){
        HotelEntity hotel = hotelService.getHotelEntity(hotelId);
        Integer totalRooms = hotel.getRooms().size();
        Integer occupiedRooms = hotelService.occupiedRooms(hotel);
        HotelDailyMetricsEntity hotelDailyMetrics = HotelDailyMetricsEntity.builder()
                .date(LocalDate.now())
                .occupancyRate(occupiedRooms.doubleValue()/totalRooms.doubleValue())
                .dailyRevenue(BigDecimal.ZERO)
                .totalRooms(totalRooms)
                .occupiedRooms(occupiedRooms)
                .availableRooms(totalRooms-occupiedRooms)
                .checkIns(hotelService.totalCheckIns(hotel))
                .checkOuts(hotelService.totalCheckOuts(hotel))
                .totalBookings(0)
                .totalSearches(0)
                .cancellations(0)
                .averageRating(hotel.getRating())
                .hotel(hotel)
                .build();
        hotelDailyMetricsRepository.save(hotelDailyMetrics);
    }

    @Override
    @Transactional
    public void updateBookingMetrics(Long hotelId, Long reservationId){
        HotelDailyMetricsEntity hotelDailyMetrics = hotelDailyMetricsRepository.findByHotelId(hotelId, LocalDate.now());
        HotelReservationEntity reservation = hotelReservationService.getReservationById(reservationId);
        HotelEntity hotel = hotelService.getHotelEntity(hotelId);
        if(reservation.getReservationDate().toLocalDate().equals(LocalDate.now())) {
            Integer totalRooms = hotel.getRooms().size();
            Integer occupiedRooms = hotelService.occupiedRooms(hotel);
            hotelDailyMetrics.setOccupancyRate(occupiedRooms.doubleValue()/totalRooms.doubleValue());
            hotelDailyMetrics.setOccupiedRooms(occupiedRooms);
            hotelDailyMetrics.setAvailableRooms(totalRooms-occupiedRooms);
        }
        hotelDailyMetrics.setTotalBookings(hotelDailyMetrics.getTotalBookings()+1);
        hotelDailyMetrics.setDailyRevenue(hotelDailyMetrics.getDailyRevenue().add(reservation.getTotalPrice()));

        hotelDailyMetricsRepository.save(hotelDailyMetrics);

        HotelDailyMetricsDTO dto = hotelDailyMetricsMapper.toDTO(hotelDailyMetrics);
        messagingTemplate.convertAndSend(
                "/topic/hotel" + hotelId + "/metrics",
                dto
        );
    }

    @Override
    @Transactional
    public void updateCancellationMetrics(Long hotelId) {
        HotelDailyMetricsEntity hotelDailyMetrics = hotelDailyMetricsRepository.findByHotelId(hotelId, LocalDate.now());
        hotelDailyMetrics.setCancellations(hotelDailyMetrics.getCancellations()+1);
        hotelDailyMetricsRepository.save(hotelDailyMetrics);

        HotelDailyMetricsDTO dto = hotelDailyMetricsMapper.toDTO(hotelDailyMetrics);
        messagingTemplate.convertAndSend(
                "/topic/hotel" + hotelId + "/metrics",
                dto
        );
    }

    @Override
    @Transactional
    public void updateCommentMetrics(Long hotelId) {
        HotelDailyMetricsEntity hotelDailyMetrics = hotelDailyMetricsRepository.findByHotelId(hotelId, LocalDate.now());
        HotelEntity hotel = hotelService.getHotelEntity(hotelId);
        hotelDailyMetrics.setAverageRating(hotel.getRating());
        hotelDailyMetricsRepository.save(hotelDailyMetrics);

        HotelDailyMetricsDTO dto = hotelDailyMetricsMapper.toDTO(hotelDailyMetrics);
        messagingTemplate.convertAndSend(
                "/topic/hotel" + hotelId + "/metrics",
                dto
        );
    }

    @Override
    @Transactional
    public void updateTotalSearches(Long hotelId){
        HotelDailyMetricsEntity hotelDailyMetrics = hotelDailyMetricsRepository.findByHotelId(hotelId, LocalDate.now());
        hotelDailyMetrics.setTotalSearches(hotelDailyMetrics.getTotalSearches()+1);
        hotelDailyMetricsRepository.save(hotelDailyMetrics);

        HotelDailyMetricsDTO dto = hotelDailyMetricsMapper.toDTO(hotelDailyMetrics);
        messagingTemplate.convertAndSend(
                "/topic/hotel" + hotelId + "/metrics",
                dto
        );

    }

    @Override
    public HotelDailyMetricsDTO getDailyMetric(Long hotelId) {
        return hotelDailyMetricsMapper.toDTO(hotelDailyMetricsRepository.findByHotelId(hotelId, LocalDate.now()));
    }

    @Override
    public List<HotelDailyMetricsDTO> getLast30DaysMetrics(Long hotelId) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDay = today.minusDays(1);
        return hotelDailyMetricsMapper.toDTOList(hotelDailyMetricsRepository.findLast30DaysMetrics(hotelId, firstDayOfMonth, lastDay));
    }

    @Override
    public HotelDashboardDTO getDashboard(Long hotelId) {
        HotelDashboardDTO hotelDashboard = HotelDashboardDTO.builder()
                .realTimeMetrics(getDailyMetric(hotelId))
                .historicalMetrics(getLast30DaysMetrics(hotelId))
                .createdAt(LocalDateTime.now())
                .build();
        return hotelDashboard;
    }
}
