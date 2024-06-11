package com.mstransaction.mstransaction.service;

import com.mstransaction.mstransaction.dto.DepositDTO;

public interface ITransactionService {
    void processDeposit(DepositDTO depositRequest);
    DepositDTO getDepositDetail(String numberAccount);
}
