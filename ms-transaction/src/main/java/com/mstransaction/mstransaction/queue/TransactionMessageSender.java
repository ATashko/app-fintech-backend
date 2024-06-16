package com.mstransaction.mstransaction.queue;

import com.mstransaction.mstransaction.configuration.RabbitMQConfig;
import com.mstransaction.mstransaction.dto.DepositDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionMessageSender {


    private final RabbitTemplate rabbitTemplate;

    public TransactionMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendDepositResponseMessage(DepositDTO depositResponseDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEPOSIT_QUEUE, depositResponseDTO);
    }

}
