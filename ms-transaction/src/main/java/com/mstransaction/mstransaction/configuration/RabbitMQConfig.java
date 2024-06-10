package com.mstransaction.mstransaction.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String CREATE_ACCOUNT_QUEUE = "create-account-queue";
    public static final String DEPOSIT_REQUEST_QUEUE = "depositRequestQueue";
    public static final String DEPOSIT_RESPONSE_QUEUE = "depositResponseQueue";
    @Bean
    public Queue createAccountQueue() {
        return new Queue(CREATE_ACCOUNT_QUEUE, true);
    }

    @Bean
    public Queue depositRequestQueue() {
        return new Queue(DEPOSIT_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue depositResponseQueue() {
        return new Queue(DEPOSIT_RESPONSE_QUEUE, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}


