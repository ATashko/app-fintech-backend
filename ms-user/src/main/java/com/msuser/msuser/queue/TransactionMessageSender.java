package com.msuser.msuser.queue;

import com.msuser.msuser.configuration.RabbitMQConfig;
import com.msuser.msuser.dto.DepositDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
/*
En esta clase deven alojarse los metodos que envian mensajes a transaction
que sean relativos a transacciones, como deposito de dinero o traspaso de dinero a otro usuario
*/
@Service
public class TransactionMessageSender {


    private final RabbitTemplate rabbitTemplate;

    public TransactionMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDepositMessage(DepositDTO depositRequest) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEPOSIT_QUEUE, depositRequest);
    }



}
