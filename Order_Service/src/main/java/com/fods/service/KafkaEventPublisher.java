package com.fods.service;

import com.fods.service.publisher.EventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public <T> void publishKafkaEvent(String topicName, T event) {
        kafkaTemplate.send(topicName, event);
    }
}
