package com.asier.SistemaReservas.hotel.hotelDashboard.event.listener;

import com.asier.SistemaReservas.hotel.hotelDashboard.event.records.CommentCreatedEvent;
import com.asier.SistemaReservas.hotel.hotelDashboard.event.records.HotelReservationCancelledEvent;
import com.asier.SistemaReservas.hotel.hotelDashboard.event.records.HotelReservationCreatedEvent;
import com.asier.SistemaReservas.hotel.hotelDashboard.event.records.HotelSearchEvent;
import com.asier.SistemaReservas.hotel.hotelDashboard.service.HotelDailyMetricsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardEventListener {
    private final HotelDailyMetricsService hotelDailyMetricsService;

    @EventListener
    @Async
    @Transactional
    public void onCommentCreated(CommentCreatedEvent event){
        try {
            hotelDailyMetricsService.updateCommentMetrics(event.hotelId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    @Async
    @Transactional
    public void onHotelReservationCreated(HotelReservationCreatedEvent event){
        try{
            hotelDailyMetricsService.updateBookingMetrics(event.hotelId(), event.reservationId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    @Async
    @Transactional
    public void onHotelReservationCancelled(HotelReservationCancelledEvent event){
        try{
            hotelDailyMetricsService.updateCancellationMetrics(event.hotelId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    @Async
    @Transactional
    public void onHotelSearch(HotelSearchEvent event){
        try{
            hotelDailyMetricsService.updateTotalSearches(event.hotelId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
