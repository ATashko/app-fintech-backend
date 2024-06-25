package com.mstransaction.mstransaction.service;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.dto.DepositDTO;

import java.util.List;

public interface ITransactionService {
    DepositDTO processDeposit(DepositDTO depositDTO);
    DepositDTO getDepositDetail(String numberAccount);
    List<Transaction> getAllTransactions(String userId);



}
