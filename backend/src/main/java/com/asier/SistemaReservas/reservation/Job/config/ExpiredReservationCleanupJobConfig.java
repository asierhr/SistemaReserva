package com.asier.SistemaReservas.reservation.Job.config;

import com.asier.SistemaReservas.reservation.Job.ExpiredReservationCleanupJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpiredReservationCleanupJobConfig {
    @Bean
    public JobDetail ExpiredReservationCleanupJobDetails(){
        return JobBuilder.newJob(ExpiredReservationCleanupJob.class)
                .withIdentity("expiredReservationJob")
                .withDescription("")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger ExpiredReservationCleanupJobTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob(ExpiredReservationCleanupJobDetails())
                .withIdentity("expiredReservationJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?"))
                .build();
    }
}
