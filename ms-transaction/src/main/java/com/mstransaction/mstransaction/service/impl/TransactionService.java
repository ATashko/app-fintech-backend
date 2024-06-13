package com.mstransaction.mstransaction.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.client.IConverterClient;
import com.mstransaction.mstransaction.domain.*;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.dto.TransactionRequestDTO;
import com.mstransaction.mstransaction.dto.TransactionResponseDTO;
import com.mstransaction.mstransaction.queue.TransactionMessageSender;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransactionRepository;
import com.mstransaction.mstransaction.repository.TransactionsAccountRepository;
import com.mstransaction.mstransaction.service.ITransactionService;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService implements ITransactionService {


    private final TransactionMessageSender transactionMessageSender;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionsAccountRepository transactionsAccountRepository;

    private final IConverterClient converterClient;

    Logger log;

    public TransactionService(TransactionMessageSender transactionMessageSender, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionsAccountRepository transactionsAccountRepository, IConverterClient converterClient) {
        this.transactionMessageSender = transactionMessageSender;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionsAccountRepository = transactionsAccountRepository;
        this.converterClient = converterClient;
    }

    @Transactional
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

    @Transactional
    @Override
    public TransactionResponseDTO proccessTransaction(TransactionRequestDTO transactionDTO) {

        Account sourceAccount;
        Account destinationAccount;

        if (!(transactionDTO.getValueToTransfer() > 0)) {
            throw new IllegalArgumentException("Ingresa un valor positivo a transferir");
        }
        if (!(transactionDTO.getShippingCurrency() != null || transactionDTO.getReceiptCurrency() != null)) {
            throw new IllegalArgumentException("Ingresa las divisas para realizar la transferencia");
        }

        if (Objects.equals(transactionDTO.getSourceAccountNumber(), transactionDTO.getDestinationAccountNumber())) {
            throw new IllegalArgumentException("La cuenta de destino no debe ser la misma cuenta de origen");
        }

        if (!(transactionDTO.getSourceAccountNumber().isEmpty() && transactionDTO.getDestinationAccountNumber().isEmpty())) {
            if (transactionDTO.getSourceAccountNumber().isEmpty() || transactionDTO.getDestinationAccountNumber().isEmpty()) {
                throw new IllegalArgumentException("Valida que las cuentas se hayan seleccionado correctamente, cuenta no está registrada en triwalapp");
            }

            sourceAccount = accountRepository.findByAccountNumber(transactionDTO.getSourceAccountNumber());
            destinationAccount = accountRepository.findByAccountNumber(transactionDTO.getDestinationAccountNumber());

            float sourceCurrentBalance = sourceAccount.getAmount();
            float destinationCurrentBalance = destinationAccount.getAmount();

            if (sourceCurrentBalance - transactionDTO.getValueToTransfer() <= 0) {
                throw new IllegalArgumentException("Fondos insuficientes para realizar esta transferencia");
            }
            try {
                sourceAccount.setAmount(sourceCurrentBalance - transactionDTO.getValueToTransfer());

                if (transactionDTO.getShippingCurrency().name().equals(transactionDTO.getReceiptCurrency().name())) {
                    float commissionValue = calculateComission(transactionDTO.getRate(), transactionDTO.getValueToTransfer());
                    float total = calculateTotalTransaction(commissionValue, transactionDTO.getValueToTransfer());
                    destinationAccount.setAmount(destinationCurrentBalance + total);
                    return persistTransaction(transactionDTO, sourceAccount, destinationAccount,"Transacción exitosa");
                } else {
                    float commissionValue = calculateComission(transactionDTO.getRate(), transactionDTO.getValueToTransfer());
                    float total = calculateTotalTransaction(commissionValue, transactionDTO.getValueToTransfer());
                    String response = convertedTransaccionTotal(total, transactionDTO.getShippingCurrency().toString(), transactionDTO.getReceiptCurrency().toString());
                    destinationAccount.setAmount(destinationCurrentBalance + total);
                    return persistTransaction(transactionDTO, sourceAccount, destinationAccount,response);
                }
            } catch (RuntimeException e) {
                e.getLocalizedMessage();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public TransactionResponseDTO persistTransaction(TransactionRequestDTO transactionRequestDTO, Account fromAccount, Account toAccount,String response) {
        TransactionAccount transactionAccount = new TransactionAccount();
        transactionAccount.setShippingCurrency(transactionRequestDTO.getShippingCurrency());
        transactionAccount.setReceiptCurrency(transactionRequestDTO.getReceiptCurrency());
        transactionAccount.setValueToTransfer(transactionRequestDTO.getValueToTransfer());

        if (fromAccount.getUserId().equals(toAccount.getUserId())) {
            transactionAccount.setTransferType(TransferType.TRANSFER_TO_MY_ACCOUNT);
        } else {
            transactionAccount.setTransferType(TransferType.TRANSFER_TO_USER);
        }

        transactionAccount.setUserId(transactionRequestDTO.getUserId());
        transactionAccount.setMethodOfPayment(MethodOfPayment.TRIWAL_TRANSFER);
        transactionAccount.setRate(transactionRequestDTO.getRate());
        transactionAccount.setRateValue(transactionRequestDTO.getRateValue());
        transactionAccount.setTransactionTotal(transactionRequestDTO.getTransactionTotal());
        transactionAccount.setConvertedTransactionTotal(transactionRequestDTO.getConvertedTransactionTotal());
        transactionAccount.setUsername(transactionRequestDTO.getUsername());
        transactionAccount.setEmail(transactionRequestDTO.getEmail());
        transactionAccount.setTransactionDetails(String.valueOf(response.matches("conversion_result"))); // obtain de converted value in methos converted to assign to attibute
        transactionAccount.setSourceAccountNumber(transactionRequestDTO.getSourceAccountNumber());
        transactionAccount.setDestinationAccountNumber(transactionRequestDTO.getDestinationAccountNumber());
        transactionAccount.setCreatedAt(new Date());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        TransactionAccount transactionAccount1 = transactionsAccountRepository.save(transactionAccount);
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO(transactionAccount1);

        return transactionResponseDTO;
    }

    public float calculateComission(float rate, float value) {
        return value * rate;
    }

    public String convertedTransaccionTotal(float totalTransaction, String originCurrency, String destinationCurrency) throws JsonProcessingException {
        String response = null;
        try {
            response = converterClient.convertCurrency(totalTransaction, originCurrency, destinationCurrency);
            Thread.sleep(1000);

        } catch (RuntimeException e) {
            e.getLocalizedMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public float calculateTotalTransaction(float comissionValue, float transferValue) {
        return transferValue - comissionValue;
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

