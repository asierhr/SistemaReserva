package com.asier.SistemaReservas.reservation.flightReservation.Job.config;

import com.asier.SistemaReservas.reservation.flightReservation.Job.NoShowFlightJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightCheckInJobConfig {
    @Bean
    public JobDetail noShowFlightJobDetails(){
        return JobBuilder.newJob(NoShowFlightJob.class)
                .withIdentity("noShowFlightJob")
                .withDescription("Verify reservations without check-in after flight")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger noShowFlightTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob(noShowFlightJobDetails())
                .withIdentity("noShowFlightTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * * * ?"))
                .build();
    }
}
