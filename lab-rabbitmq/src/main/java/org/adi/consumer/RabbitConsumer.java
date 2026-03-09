package org.adi.consumer;

import lombok.extern.slf4j.Slf4j;
import org.adi.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitConsumer {

    @RabbitListener(queues = "${rabbitmq.queue}", concurrency = "3")
    public void receiveOrder(OrderEvent event) throws InterruptedException {
        log.info("--> [Order Service] Received order for processing: {} on thread {}", event.orderId(), Thread.currentThread().getName());

        // Simulate work
        Thread.sleep(1000); // 1 second

        if (event.productDescription().contains("fail")) {
            log.warn("--> [Order Service] Simulating failure for order: {}. Triggering retries.", event.orderId());
            throw new RuntimeException("This is a simulated processing failure!");
        }

        log.info("--> [Order Service] Order {} processed successfully.", event.orderId());
    }

    @RabbitListener(queues = "${rabbitmq.email-queue}")
    public void sendConfirmationEmail(OrderEvent event) {
        log.info("--> [Email Service] Sending confirmation email for order: {}", event.orderId());
        // In a real app, this would use an email client library.
    }

    @RabbitListener(queues = "${rabbitmq.dlq.queue}")
    public void processFailedMessages(OrderEvent event) {
        log.error("--> [DLQ Handler] Received permanently failed order: {}. Storing for manual review.", event.orderId());
        // In a real app, you might save this to a DB, send an alert, etc.
    }
}
