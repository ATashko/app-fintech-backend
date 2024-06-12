package com.mstransaction.mstransaction.service;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.dto.DepositDTO;

import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    DepositDTO processDeposit(DepositDTO depositRequest);
    DepositDTO getDepositDetail(String numberAccount);
    List getAllTransactions(String userId);
}
