package com.asier.SistemaReservas.user.event.Kafka;

import com.asier.SistemaReservas.user.event.records.UserCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserCreatedListener {
    private final UserCreatedProducer userCreatedProducer;

    @EventListener
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(UserCreatedEvent event){
        try{
            UserCreatedEvent newUser = new UserCreatedEvent(
                    event.id(),
                    event.name(),
                    event.mail()
            );
            userCreatedProducer.sendUserCreatedEvent(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
