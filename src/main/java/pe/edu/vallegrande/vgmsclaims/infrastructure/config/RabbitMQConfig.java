package pe.edu.vallegrande.vgmsclaims.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para mensajería asíncrona
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.claims:claims-exchange}")
    private String claimsExchange;

    @Value("${rabbitmq.queue.complaints:complaints-queue}")
    private String complaintsQueue;

    @Value("${rabbitmq.queue.incidents:incidents-queue}")
    private String incidentsQueue;

    @Value("${rabbitmq.queue.urgent:urgent-incidents-queue}")
    private String urgentQueue;

    // Exchange
    @Bean
    public TopicExchange claimsExchange() {
        return new TopicExchange(claimsExchange);
    }

    // Queues
    @Bean
    public Queue complaintsQueue() {
        return QueueBuilder.durable(complaintsQueue).build();
    }

    @Bean
    public Queue incidentsQueue() {
        return QueueBuilder.durable(incidentsQueue).build();
    }

    @Bean
    public Queue urgentIncidentsQueue() {
        return QueueBuilder.durable(urgentQueue).build();
    }

    // Bindings
    @Bean
    public Binding complaintsBinding(Queue complaintsQueue, TopicExchange claimsExchange) {
        return BindingBuilder.bind(complaintsQueue)
                .to(claimsExchange)
                .with("complaint.*");
    }

    @Bean
    public Binding incidentsBinding(Queue incidentsQueue, TopicExchange claimsExchange) {
        return BindingBuilder.bind(incidentsQueue)
                .to(claimsExchange)
                .with("incident.*");
    }

    @Bean
    public Binding urgentIncidentsBinding(Queue urgentIncidentsQueue, TopicExchange claimsExchange) {
        return BindingBuilder.bind(urgentIncidentsQueue)
                .to(claimsExchange)
                .with("incident.urgent.*");
    }

    // Message Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
