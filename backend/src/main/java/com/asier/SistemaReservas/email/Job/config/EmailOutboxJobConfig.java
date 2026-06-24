package com.asier.SistemaReservas.email.Job.config;

import com.asier.SistemaReservas.email.Job.EmailOutboxJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailOutboxJobConfig {
    @Bean
    public JobDetail EmailOutboxJobDetails(){
        return JobBuilder.newJob(EmailOutboxJob.class)
                .withIdentity("emailOutboxJob")
                .withDescription("Verify emails with pending in it")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger EmailOutboxJobTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob(EmailOutboxJobDetails())
                .withIdentity("emailOutboxJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .build();
    }
}
