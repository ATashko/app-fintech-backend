package com.mstransaction.mstransaction.service;

import com.mstransaction.mstransaction.domain.Account;
import com.mstransaction.mstransaction.dto.DepositRequestDTO;
import com.mstransaction.mstransaction.dto.DepositResponseDTO;
import com.mstransaction.mstransaction.queue.TransactionMessageSender;
import com.mstransaction.mstransaction.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {



    @Autowired
    private TransactionMessageSender transactionMessageSender;

    @Autowired
    private AccountRepository accountRepository;


    @Transactional
    public void processDeposit(DepositRequestDTO depositRequest) {

        String userId = depositRequest.getUserId();
        String accountNumber = depositRequest.getAccountNumber();
        String shippingCurrency = depositRequest.getShippingCurrency();
        float valueToTransfer = depositRequest.getValueToTransfer();

        if (userId == null || userId.isEmpty() || accountNumber == null || accountNumber.isEmpty()
                || shippingCurrency == null || shippingCurrency.isEmpty() || valueToTransfer <= 0) {
            throw new IllegalArgumentException("Datos de transacción inválidos.");
        }

        Account account = accountRepository.findByUserIdAndAccountNumber(userId, accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada para el usuario dado."));

        float newAmount = account.getAmount() + valueToTransfer;
        account.setAmount(newAmount);

        accountRepository.save(account);

        DepositResponseDTO depositResponseDTO = new DepositResponseDTO(userId, accountNumber, valueToTransfer, shippingCurrency);

        transactionMessageSender.sendDepositResponseMessage(depositResponseDTO);
    }

        /*transaction.setUserId(userId);
        //transaction.setAccountNumber(accountNumber); ---REVISAR---
        transaction.setValueToTransfer(valueToTransfer);
        transaction.setShippingCurrency(ShippingCurrency.valueOf(shippingCurrency));
        transaction.setTransferType(TransferType.valueOf("DEPOSIT"));*/


}

