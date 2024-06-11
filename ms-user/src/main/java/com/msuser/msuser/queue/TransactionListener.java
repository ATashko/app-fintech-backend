package com.msuser.msuser.queue;

import com.msuser.msuser.dto.DepositDTO;
import com.msuser.msuser.service.impl.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "depositQueue")
    public void processDepositResponse(DepositDTO depositResponse, String email) throws IOException {
        System.out.println("Detalles de la transacción recibidos: " + depositResponse);

        String userId = depositResponse.getUserId();
        emailService.sendDepositVerificationEmail(email, userId);

    }
}

