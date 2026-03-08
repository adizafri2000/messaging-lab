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
        return new Queue(dlqName); // DLQs are durable by default
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(dlqRoutingKey);
    }

    // 1. Define the Queue (The Bucket)
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlxName)
                .withArgument("x-dead-letter-routing-key", dlqRoutingKey)
                .build();
    }

    // 2. Define the Exchange (The Router)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    // 3. Bind them together with a Routing Key
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // 4. IMPORTANT: Convert Java Objects to JSON automatically
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
