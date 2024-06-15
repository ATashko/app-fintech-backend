package com.mstransaction.mstransaction.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.client.IConverterClient;
import com.mstransaction.mstransaction.domain.Account;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.dto.TransferenceRequestDTO;
import com.mstransaction.mstransaction.dto.TransferenceResponseDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransferenceRepository;
import com.mstransaction.mstransaction.service.ITransferenceService;
import jakarta.servlet.ServletOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class TransferenceService implements ITransferenceService {

    private final TransferenceRepository transferenceRepository;

    private final IConverterClient converterClient;

    private final AccountRepository accountRepository;

    public TransferenceService(TransferenceRepository transferenceRepository, IConverterClient converterClient, AccountRepository accountRepository) {
        this.transferenceRepository = transferenceRepository;
        this.converterClient = converterClient;
        this.accountRepository = accountRepository;
    }


    @Transactional
    @Override
    public TransferenceResponseDTO processTransference(TransferenceRequestDTO transactionDTO) {

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
                    float commissionValue = calculateCommission(transactionDTO.getRate(), transactionDTO.getValueToTransfer());
                    float total = calculateTotalTransaction(commissionValue, transactionDTO.getValueToTransfer());
                    destinationAccount.setAmount(destinationCurrentBalance + total);
                    return persistTransference(transactionDTO, sourceAccount, destinationAccount,total);
                } else {
                    float commissionValue = calculateCommission(transactionDTO.getRate(), transactionDTO.getValueToTransfer());
                    float total = calculateTotalTransaction(commissionValue, transactionDTO.getValueToTransfer());
                    float convertedValue = convertedTransference(total, transactionDTO.getShippingCurrency().toString(), transactionDTO.getReceiptCurrency().toString());
                    destinationAccount.setAmount(destinationCurrentBalance + convertedValue);
                    return persistTransference(transactionDTO, sourceAccount, destinationAccount,convertedValue);
                }
            } catch (RuntimeException e) {
                e.getLocalizedMessage();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public Set<TransferenceResponseDTO> getAllTransferences() {
        return null;
    }

    public TransferenceResponseDTO persistTransference(TransferenceRequestDTO transferenceRequestDTO, Account fromAccount, Account toAccount, float value) {
        Transference transference = new Transference();
        transference.setShippingCurrency(transferenceRequestDTO.getShippingCurrency());
        transference.setReceiptCurrency(transferenceRequestDTO.getReceiptCurrency());
        transference.setValueToTransfer(transferenceRequestDTO.getValueToTransfer());
        if (fromAccount.getUserId().equals(toAccount.getUserId())) {
            transference.setTransferType(TransferType.TRANSFER_TO_MY_ACCOUNT);
        } else {
            transference.setTransferType(TransferType.TRANSFER_TO_USER);
        }
        transference.setUserId(transferenceRequestDTO.getUserId());
        transference.setMethodOfPayment(MethodOfPayment.TRIWAL_TRANSFER);
        transference.setRate(transferenceRequestDTO.getRate());
        transference.setRateValue(transferenceRequestDTO.getRateValue());
        transference.setTransactionTotal(transferenceRequestDTO.getTransactionTotal());
        transference.setConvertedTransactionTotal(value);
        transference.setUsername(transferenceRequestDTO.getUsername());
        transference.setEmail(transferenceRequestDTO.getEmail());
        if(!transferenceRequestDTO.getShippingCurrency().name().equals(transferenceRequestDTO.getReceiptCurrency().name())){
            transference.setTransactionDetails("Transacción con conversión");
        }else{
            transference.setTransactionDetails("Transacción misma moneda");
        }

        transference.setSourceAccountNumber(transferenceRequestDTO.getSourceAccountNumber());
        transference.setDestinationAccountNumber(transferenceRequestDTO.getDestinationAccountNumber());
        transference.setCreatedAt(new Date());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transference transference1 = transferenceRepository.save(transference);

        return new TransferenceResponseDTO(transference1);
    }

    public float calculateCommission(float rate, float value) {
        return value * rate;
    }

    public float convertedTransference(float totalTransaction, String originCurrency, String destinationCurrency) throws JsonProcessingException {
        String response = null;
        float convertedResult = 0.000f;
        try {
            response = converterClient.convertCurrency(totalTransaction, originCurrency, destinationCurrency);
            String[] res = response.split(",");
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String number = res[12].replaceAll("[^0-9]", "");
            String formattedNumber = decimalFormat.format(number);
            convertedResult = (float) decimalFormat.parse(formattedNumber);
            Thread.sleep(1000);

        } catch (InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("+++++" + convertedResult); // todo: replace al values with bigDecimal
        System.out.println("-----------------------------------------------------------");
        return convertedResult;
    }

    public float calculateTotalTransaction(float commissionValue, float transferValue) {
        return transferValue - commissionValue;
    }
}
