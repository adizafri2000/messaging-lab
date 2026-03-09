package org.adi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.email-queue}")
    private String emailQueueName;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.dlq.queue}")
    private String dlqName;

    @Value("${rabbitmq.dlq.exchange}")
    private String dlxName;

    @Value("${rabbitmq.dlq.routing-key}")
    private String dlqRoutingKey;

    // --- Dead Letter Queue Infrastructure ---

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlxName);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(dlqName);
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(dlqRoutingKey);
    }

    // --- Main Order Processing Infrastructure ---

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlxName)
                .withArgument("x-dead-letter-routing-key", dlqRoutingKey)
                .build();
    }

    // --- Email Notification Infrastructure ---

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName, true); // durable=true
    }

    // --- Fanout Exchange (The Broadcaster) ---

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchangeName);
    }

    // --- Bindings ---

    @Bean
    public Binding orderBinding(Queue orderQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(orderQueue).to(fanoutExchange);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(emailQueue).to(fanoutExchange);
    }

    // --- JSON Converter ---

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
