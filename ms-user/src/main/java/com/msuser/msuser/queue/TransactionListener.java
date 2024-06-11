package com.msuser.msuser.queue;

import com.msuser.msuser.configuration.RabbitMQConfig;
import com.msuser.msuser.dto.DepositDTO;
import com.msuser.msuser.service.impl.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @RabbitListener(queues = RabbitMQConfig.DEPOSIT_QUEUE)
    public void processDepositResponse(DepositDTO depositResponse) throws IOException {


        String userId = depositResponse.getUserId();
        String account = depositResponse.getAccountNumber();
        String currency = depositResponse.getShippingCurrency();
        float value = depositResponse.getValueToTransfer();
        String email = depositResponse.getEmail();
        System.out.println(userId + account + currency + value + email);

        emailService.sendDepositVerificationEmail(email, userId);

    }
}

