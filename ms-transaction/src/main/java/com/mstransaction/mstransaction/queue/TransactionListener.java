package com.mstransaction.mstransaction.queue;

import com.mstransaction.mstransaction.configuration.RabbitMQConfig;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.service.impl.TransactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {


    private final AccountRepository accountRepository;


    private final TransactionMessageSender transactionMessageSender;


    private final TransactionService transactionService;

    public TransactionListener(AccountRepository accountRepository, TransactionMessageSender transactionMessageSender, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionMessageSender = transactionMessageSender;
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = RabbitMQConfig.DEPOSIT_QUEUE)
    public void handleDeposit(DepositDTO depositRequest) {
        transactionService.processDeposit(depositRequest);
    }




}

