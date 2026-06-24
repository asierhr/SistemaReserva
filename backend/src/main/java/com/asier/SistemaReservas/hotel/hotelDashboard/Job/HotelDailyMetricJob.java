package com.asier.SistemaReservas.hotel.hotelDashboard.Job;

import com.asier.SistemaReservas.hotel.domain.entity.HotelEntity;
import com.asier.SistemaReservas.hotel.hotelDashboard.service.HotelDailyMetricsService;
import com.asier.SistemaReservas.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelDailyMetricJob extends QuartzJobBean {
    private final HotelDailyMetricsService hotelDailyMetricsService;
    private final HotelService hotelService;

    @Override
    protected void executeInternal(JobExecutionContext context){
        try {
            List<HotelEntity> hotels = hotelService.getAllHotels();
            for (HotelEntity hotel : hotels) {
                hotelDailyMetricsService.createDailyMetric(hotel.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
