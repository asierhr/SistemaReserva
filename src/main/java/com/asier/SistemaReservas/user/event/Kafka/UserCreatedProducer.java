package com.asier.SistemaReservas.user.event.Kafka;

import com.asier.SistemaReservas.user.event.records.UserCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCreatedProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${topics.user-created}")
    private String topic;

    public void sendUserCreatedEvent(UserCreatedEvent event) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(topic,message);
    }
}
