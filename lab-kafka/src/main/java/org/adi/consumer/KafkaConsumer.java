package org.adi.consumer;

import lombok.extern.slf4j.Slf4j;
import org.adi.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveOrder(OrderEvent event) {
        log.info("--> [Kafka Consumer] Received order: {}", event.orderId());
        // In a real app, you would process the order here.
    }
}
