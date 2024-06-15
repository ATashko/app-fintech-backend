package com.msuser.msuser.queue;

import com.msuser.msuser.configuration.RabbitMQConfig;
import com.msuser.msuser.dto.DepositDTO;
import com.msuser.msuser.service.impl.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionListener {


    private final EmailService emailService;


    private final RabbitTemplate rabbitTemplate;

    public TransactionListener(EmailService emailService, RabbitTemplate rabbitTemplate) {
        this.emailService = emailService;
        this.rabbitTemplate = rabbitTemplate;
    }


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

