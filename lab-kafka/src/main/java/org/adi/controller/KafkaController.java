package org.adi.controller;

import lombok.extern.slf4j.Slf4j;
import org.adi.dto.OrderEvent;
import org.adi.service.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/kafka")
@Slf4j
public class KafkaController {

    private final KafkaProducer producer;

    public KafkaController(KafkaProducer producer) {
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
        log.info("Sending order event via Kafka: {}", event);
        producer.sendOrder(event);
        return "Message sent to Kafka!";
    }
}
