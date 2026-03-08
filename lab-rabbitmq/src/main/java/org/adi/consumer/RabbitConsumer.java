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
    }
}