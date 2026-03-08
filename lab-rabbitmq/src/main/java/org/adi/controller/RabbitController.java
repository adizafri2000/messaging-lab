package org.adi.controller;

import lombok.extern.slf4j.Slf4j;
import org.adi.dto.OrderEvent;
import org.adi.service.RabbitProducer;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/rabbit")
@Slf4j
public class RabbitController {
    private final RabbitProducer producer;

    public RabbitController(RabbitProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String send(@RequestParam String desc, @RequestParam double amount) {
        
        OrderEvent event = new OrderEvent(
                UUID.randomUUID().toString(),
                desc,
                amount,
                LocalDateTime.now()
        );
        log.info("Sending order event: {}", event);
        producer.sendOrder(event);
        return "Message sent to RabbitMQ!";
    }
}