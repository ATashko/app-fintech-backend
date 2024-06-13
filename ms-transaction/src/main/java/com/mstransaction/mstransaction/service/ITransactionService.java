package com.mstransaction.mstransaction.service;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.dto.TransactionRequestDTO;
import com.mstransaction.mstransaction.dto.TransactionResponseDTO;

import java.util.List;

public interface ITransactionService {
    DepositDTO processDeposit(DepositDTO depositRequest);
    DepositDTO getDepositDetail(String numberAccount);
    List<Transaction> getAllTransactions(String userId);
    TransactionResponseDTO proccessTransaction(TransactionRequestDTO transactionDTO);
}
