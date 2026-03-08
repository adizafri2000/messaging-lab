package org.adi.consumer;

import lombok.extern.slf4j.Slf4j;
import org.adi.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitConsumer {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveOrder(OrderEvent event) {
        log.info("Received Order via RabbitMQ: {}", event);

        // Simulate a processing failure to test the DLQ
        if (event.productDescription().contains("fail")) {
            log.warn("Simulating failure for order: {}. Sending to DLQ.", event.orderId());
            throw new RuntimeException("This is a simulated processing failure!");
        }
    }

    @RabbitListener(queues = "${rabbitmq.dlq.queue}")
    public void processFailedMessages(OrderEvent event) {
        log.info("Received message in DLQ: {}", event);
        // In a real app, you might save this to a DB, send an email alert, etc.
    }
}