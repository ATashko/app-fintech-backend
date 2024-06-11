package com.mstransaction.mstransaction.queue;

import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.service.impl.TransactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionListener {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionMessageSender transactionMessageSender;

    @Autowired
    private TransactionService transactionService;

    @RabbitListener(queues = "depositQueue")
    public void handleDeposit(DepositDTO depositRequest) {

        transactionService.processDeposit(depositRequest);
    }




}

