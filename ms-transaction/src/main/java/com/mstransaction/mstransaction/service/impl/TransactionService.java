package com.mstransaction.mstransaction.service.impl;

import com.mstransaction.mstransaction.domain.*;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.dto.TransactionDTO;
import com.mstransaction.mstransaction.queue.TransactionMessageSender;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransactionRepository;
import com.mstransaction.mstransaction.service.ITransactionService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionMessageSender transactionMessageSender;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    Logger log;


    public DepositDTO processDeposit(DepositDTO depositDTO) {

        String userId = depositDTO.getUserId();
        String accountNumber = depositDTO.getAccountNumber();
        String shippingCurrency = depositDTO.getShippingCurrency();
        String email = depositDTO.getEmail();
        float valueToTransfer = depositDTO.getValueToTransfer();

        if (userId == null || userId.isEmpty() || accountNumber == null || accountNumber.isEmpty()
                || shippingCurrency == null || shippingCurrency.isEmpty() || valueToTransfer <= 0
        || email.isEmpty() || email == null) {
            throw new IllegalArgumentException("Datos de transacción inválidos.");
        }

        Transaction transaction = new Transaction();
        Account account = accountRepository.findByUserIdAndAccountNumber(userId, accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada para el usuario dado."));

        float newAmount = account.getAmount() + valueToTransfer;
        account.setAmount(newAmount);
        accountRepository.save(account);

        transaction.setUserId(userId);
        transaction.setTransferType(TransferType.valueOf("DEPOSIT"));
        transaction.setMethodOfPayment(MethodOfPayment.valueOf("CASH"));
        transaction.setAccountNumber(accountNumber);
        transaction.setShippingCurrency(ShippingCurrency.valueOf(shippingCurrency));
        transaction.setEmail(email);
        transaction.setValueToTransfer(valueToTransfer);
        System.out.println(transaction.getAccountNumber());
        transactionRepository.save(transaction);

        DepositDTO deposit = new DepositDTO(userId, accountNumber, valueToTransfer, shippingCurrency, email);

        //transactionMessageSender.sendDepositResponseMessage(deposit);
        return deposit;
    }

    @Override
    public TransactionDTO proccessTransaction(TransactionDTO transaction) {
        return null;
    }






    @Override
    public DepositDTO getDepositDetail(String numberAccount) {
        Transaction transaction = transactionRepository.findFirstByAccountNumberOrderByTransactionIdDesc(numberAccount)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ninguna transacción para la cuenta número: " + numberAccount));

        return new DepositDTO(
                transaction.getUserId(),
                transaction.getAccountNumber(),
                transaction.getValueToTransfer(),
                transaction.getShippingCurrency().toString(),
                transaction.getEmail()

        );
    }
    @Override
    public List<Transaction> getAllTransactions(String userId) {
        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
        return transactions;
    }





        /*transaction.setUserId(userId);
        //transaction.setAccountNumber(accountNumber); ---REVISAR---
        transaction.setValueToTransfer(valueToTransfer);
        transaction.setShippingCurrency(ShippingCurrency.valueOf(shippingCurrency));
        transaction.setTransferType(TransferType.valueOf("DEPOSIT"));*/

}

