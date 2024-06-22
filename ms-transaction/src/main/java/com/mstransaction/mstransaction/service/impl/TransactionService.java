package com.mstransaction.mstransaction.service.impl;

import com.mstransaction.mstransaction.domain.*;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.queue.TransactionMessageSender;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransactionRepository;
import com.mstransaction.mstransaction.service.ITransactionService;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TransactionService implements ITransactionService {

    private final TransactionMessageSender transactionMessageSender;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;


    Logger log;

    public TransactionService(TransactionMessageSender transactionMessageSender, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.transactionMessageSender = transactionMessageSender;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }



    @Override
    public DepositDTO processDeposit(DepositDTO depositDTO) {

        String userId = depositDTO.getUserId();
        String accountNumber = depositDTO.getAccountNumber();
        String shippingCurrency = depositDTO.getShippingCurrency();
        String email = depositDTO.getEmail();
        BigDecimal valueToTransfer = depositDTO.getValueToTransfer();

        if (userId == null || userId.isEmpty() || accountNumber == null || accountNumber.isEmpty()
                || shippingCurrency == null || shippingCurrency.isEmpty() || valueToTransfer.compareTo(BigDecimal.ZERO) <= 0
                || email.isEmpty() || email == null) {
            throw new IllegalArgumentException("Datos de transacción inválidos.");
        }

        Transaction transaction = new Transaction();
        Account account = accountRepository.findByUserIdAndAccountNumber(userId, accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada para el usuario dado."));


        BigDecimal newAmount = account.getAmount();
        account.setAmount(newAmount.add(valueToTransfer));
        accountRepository.save(account);

        transaction.setUserId(userId);
        transaction.setTransferType(TransferType.valueOf("DEPOSIT"));
        transaction.setMethodOfPayment(MethodOfPayment.valueOf("CASH"));
        transaction.setAccountNumber(accountNumber);
        transaction.setShippingCurrency(ShippingCurrency.valueOf(shippingCurrency));
        transaction.setEmail(email);
        transaction.setValueToTransfer(valueToTransfer);
        transaction.setCreatedAt(new Date());
        System.out.println(transaction.getAccountNumber());
        transactionRepository.save(transaction);

        //transactionMessageSender.sendDepositResponseMessage(deposit);
        return new DepositDTO(userId, accountNumber, valueToTransfer, shippingCurrency, email);
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
        return transactionRepository.findAllByUserId(userId);
    }



        /*transaction.setUserId(userId);
        //transaction.setAccountNumber(accountNumber); ---REVISAR---
        transaction.setValueToTransfer(valueToTransfer);
        transaction.setShippingCurrency(ShippingCurrency.valueOf(shippingCurrency));
        transaction.setTransferType(TransferType.valueOf("DEPOSIT"));*/

}

