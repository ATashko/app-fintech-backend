package com.mstransaction.mstransaction.queue;

import com.mstransaction.mstransaction.dto.DepositRequestDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.service.TransactionService;
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

    @RabbitListener(queues = "depositRequestQueue")
    public void handleDeposit(DepositRequestDTO depositRequest) {
        transactionService.processDeposit(depositRequest);
    }


}

