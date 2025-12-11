package com.asier.SistemaReservas.hotel.hotelDashboard.Job.config;

import com.asier.SistemaReservas.hotel.hotelDashboard.Job.HotelDailyMetricJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelDailyMetricJobConfig {
    @Bean
    public JobDetail HotelDailyMetricJobDetails(){
        return JobBuilder.newJob(HotelDailyMetricJob.class)
                .withIdentity("hotelDailyMetricJob")
                .withDescription("Creating dashboards at 12AM")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger HotelDailyMetricJobTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob(HotelDailyMetricJobDetails())
                .withIdentity("hotelDailyMetricTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
    }
}
