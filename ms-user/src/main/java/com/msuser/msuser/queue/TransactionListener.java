package com.msuser.msuser.queue;

import com.msuser.msuser.dto.DepositResponseDTO;
import com.msuser.msuser.util.TokenProvider;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionListener {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "depositResponseQueue")
    public void processDepositResponse(DepositResponseDTO depositResponse) {
        System.out.println("Detalles de la transacción recibidos: " + depositResponse);

        String userId = depositResponse.getUserId();
        try {
            String result = TokenProvider.sendDepositVerificationEmail(userId);
            System.out.println("Correo de confirmación: " + result);
        } catch (IOException e) {
            System.err.println("Error al enviar el correo de confirmación: " + e.getMessage());
        }
    }
}

