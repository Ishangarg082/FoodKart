package com.fods.service.publisher;

public interface EventPublisher {
    <T> void publishKafkaEvent(String topicName, T event);
}
