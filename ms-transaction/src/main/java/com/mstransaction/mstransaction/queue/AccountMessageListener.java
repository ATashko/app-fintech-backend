package com.mstransaction.mstransaction.queue;

import com.mstransaction.mstransaction.configuration.RabbitMQConfig;
import com.mstransaction.mstransaction.dto.AccountDTO;
import com.mstransaction.mstransaction.service.impl.AccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AccountMessageListener {
	
    private final AccountService accountService;

    public AccountMessageListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = RabbitMQConfig.CREATE_ACCOUNT_QUEUE)
    public void receiveCreateAccountMessage(AccountDTO accountDTO) {
        System.out.println("Received message: " + accountDTO);
        accountService.createAccount(accountDTO);
    }
}

