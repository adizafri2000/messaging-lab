package org.adi.service;

import lombok.extern.slf4j.Slf4j;
import org.adi.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    @Value("${kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(OrderEvent event) {
        log.info("Sending Order to Kafka topic '{}': {}", topic, event.orderId());
        kafkaTemplate.send(topic, event);
    }
}
