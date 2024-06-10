package com.msuser.msuser.queue;

import com.msuser.msuser.configuration.RabbitMQConfig;
import com.msuser.msuser.dto.AccountDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public AccountMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCreateAccountMessage(AccountDTO accountDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CREATE_ACCOUNT_QUEUE, accountDTO);
    }
}

