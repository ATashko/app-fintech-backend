package com.msuser.msuser.queue;

import com.msuser.msuser.configuration.RabbitMQConfig;
import com.msuser.msuser.dto.DepositDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
En esta clase deven alojarse los metodos que envian mensajes a transaction
que sean relativos a transacciones, como deposito de dinero o traspaso de dinero a otro usuario
*/
@Service
public class TransactionMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendDepositMessage(DepositDTO depositRequest) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEPOSIT_QUEUE, depositRequest);
    }



}
