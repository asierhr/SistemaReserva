package com.asier.SistemaReservas.email.Job;

import com.asier.SistemaReservas.email.domain.entity.EmailOutboxEntity;
import com.asier.SistemaReservas.reservation.flightReservation.domain.entity.FlightReservationEntity;
import com.asier.SistemaReservas.reservation.hotelReservation.domain.entity.HotelReservationEntity;
import com.asier.SistemaReservas.email.domain.enums.OutboxStatus;
import com.asier.SistemaReservas.email.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class EmailOutboxJob extends QuartzJobBean{
    private final EmailService emailService;

    private static final int BASE_DELAY_SECONDS = 60;
    private static final int LIMIT_EMAIL_PROCESSING = 10;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException{
        List<EmailOutboxEntity> pendingEmails = emailService.findEmailsPendingToProcess(LIMIT_EMAIL_PROCESSING);
        if (pendingEmails.isEmpty()) return;
        for(EmailOutboxEntity emailOutbox: pendingEmails) processEmail(emailOutbox);
    }

    @Transactional
    private void processEmail(EmailOutboxEntity outbox){
        try{
            outbox.setOutboxStatus(OutboxStatus.PROCESSING);
            outbox.setLastAttemptAt(LocalDateTime.now());
            emailService.updateEmailOutbox(outbox);

            if (outbox.getReservation() instanceof FlightReservationEntity flightRes) {
                Hibernate.initialize(flightRes.getSeat());
                Hibernate.initialize(flightRes.getFlight());
            } else if (outbox.getReservation() instanceof HotelReservationEntity hotelRes) {
                Hibernate.initialize(hotelRes.getRooms());
                Hibernate.initialize(hotelRes.getHotel());
            }

            emailService.sendReservationConfirmation(outbox.getUser(), outbox.getReservation(), outbox.getQrCodeBase64());

            outbox.setOutboxStatus(OutboxStatus.SENT);
            outbox.setSentAt(LocalDateTime.now());
            emailService.updateEmailOutbox(outbox);

        } catch (Exception e) {
            handleError(outbox,e);
        }
    }

    private void handleError(EmailOutboxEntity outbox, Exception e){
        outbox.setAttempts(outbox.getAttempts() + 1);
        if(outbox.getAttempts() >= 5){
            outbox.setOutboxStatus(OutboxStatus.FAILED);
            outbox.setNextRetryAt(null);
        }else {
            outbox.setOutboxStatus(OutboxStatus.PENDING);
            int delayedMinutes = BASE_DELAY_SECONDS * (int) Math.pow(2, outbox.getAttempts());
            outbox.setNextRetryAt(LocalDateTime.now().plusSeconds(delayedMinutes));
        }
        emailService.updateEmailOutbox(outbox);
    }
}
