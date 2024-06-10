package com.mstransaction.mstransaction.queue;

import com.mstransaction.mstransaction.dto.DepositResponseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDepositResponseMessage(DepositResponseDTO depositResponseDTO) {
        rabbitTemplate.convertAndSend("depositResponseQueue", depositResponseDTO);
    }

}
