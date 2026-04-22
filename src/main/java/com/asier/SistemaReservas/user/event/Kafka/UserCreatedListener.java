package com.asier.SistemaReservas.user.event.Kafka;

import com.asier.SistemaReservas.user.event.records.UserCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedListener {
    private final UserCreatedProducer userCreatedProducer;

    @EventListener
    @Async
    @Transactional
    public void onUserCreated(UserCreatedEvent event){
        try{
            userCreatedProducer.sendUserCreatedEvent(event.user());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
