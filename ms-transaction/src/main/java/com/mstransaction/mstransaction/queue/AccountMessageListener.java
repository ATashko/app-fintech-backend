package com.mstransaction.mstransaction.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.mstransaction.mstransaction.configuration.RabbitMQConfig;
import com.mstransaction.mstransaction.domain.TypeAccount;
import com.mstransaction.mstransaction.dto.AccountDTO;
import com.mstransaction.mstransaction.service.impl.AccountService;

@Service
public class AccountMessageListener {

    private final AccountService accountService;

    public AccountMessageListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = RabbitMQConfig.CREATE_ACCOUNT_QUEUE)
    public void receiveCreateAccountMessage(AccountDTO accountDTO) throws Exception {
        System.out.println("Received message: " + accountDTO);
        try {
            if(TypeAccount.valueOf(accountDTO.getTypeAccount()) != null)
                   accountService.createAccount(accountDTO);
        } catch (IllegalArgumentException e) {
            System.err.println("Valor inválido para TypeAccount: " + accountDTO.getTypeAccount());
            throw new Exception(e);
        }
    }
}

