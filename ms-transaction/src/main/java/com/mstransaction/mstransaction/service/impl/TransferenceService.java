package com.mstransaction.mstransaction.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.client.IConverterClient;
import com.mstransaction.mstransaction.domain.Account;
import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import com.mstransaction.mstransaction.dto.TransferenceInfoResponseDTO;
import com.mstransaction.mstransaction.dto.TransferenceRequestDTO;
import com.mstransaction.mstransaction.dto.TransferenceResponseDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransferenceRepository;
import com.mstransaction.mstransaction.service.ITransferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public TransferenceResponseDTO processTransference(TransferenceRequestDTO transferenceRequestDTO) {
        try {

            if (transferenceRequestDTO.getTransferValue() == null || transferenceRequestDTO.getTransferValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Ingresa un valor positivo a transferir");
            }
            if (!(transferenceRequestDTO.getShippingCurrency() != null || transferenceRequestDTO.getReceiptCurrency() != null)) {
                throw new IllegalArgumentException("Ingresa las divisas para realizar la transferencia");
            }

            if (Objects.equals(transferenceRequestDTO.getSourceAccountNumber(), transferenceRequestDTO.getDestinationAccountNumber())) {
                throw new IllegalArgumentException("La cuenta de destino no debe ser la misma cuenta de origen");
            }

            if (!(transferenceRequestDTO.getSourceAccountNumber().isEmpty() && transferenceRequestDTO.getDestinationAccountNumber().isEmpty())) {
                if (transferenceRequestDTO.getSourceAccountNumber().isEmpty() || transferenceRequestDTO.getDestinationAccountNumber().isEmpty()) {
                    throw new IllegalArgumentException("Valida que las cuentas se hayan seleccionado correctamente, cuenta no está registrada en triwalapp");
                }

                Account sourceAccount = accountRepository.findByAccountNumber(transferenceRequestDTO.getSourceAccountNumber());
                Account destinationAccount = accountRepository.findByAccountNumber(transferenceRequestDTO.getDestinationAccountNumber());

                BigDecimal sourceCurrentBalance = sourceAccount.getAmount();
                BigDecimal destinationCurrentBalance = destinationAccount.getAmount();

                if (sourceCurrentBalance.subtract(transferenceRequestDTO.getTransferValue()).compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Fondos insuficientes para realizar esta transferencia");
                }

                sourceAccount.setAmount(sourceCurrentBalance.subtract(transferenceRequestDTO.getTransferValue()));

                if (transferenceRequestDTO.getShippingCurrency().name().equals(transferenceRequestDTO.getReceiptCurrency().name())) {
                    BigDecimal commissionValue = calculateCommission(new BigDecimal("0.02"), transferenceRequestDTO.getTransferValue());
                    BigDecimal total = calculateTotalTransference(commissionValue, transferenceRequestDTO.getTransferValue());
                    destinationAccount.setAmount(destinationCurrentBalance.add(total));
                    return persistTransference(transferenceRequestDTO, sourceAccount, destinationAccount, commissionValue, total);
                } else {
                    BigDecimal commissionValue = calculateCommission(new BigDecimal("0.02"), transferenceRequestDTO.getTransferValue());
                    BigDecimal total = calculateTotalTransference(commissionValue, transferenceRequestDTO.getTransferValue());
                    BigDecimal convertedValue = convertedTransference(total, transferenceRequestDTO.getShippingCurrency().toString(), transferenceRequestDTO.getReceiptCurrency().toString());
                    destinationAccount.setAmount(destinationCurrentBalance.add(convertedValue));
                    return persistTransference(transferenceRequestDTO, sourceAccount, destinationAccount, commissionValue, convertedValue);
                }
            }
        } catch (IllegalArgumentException | JsonProcessingException e) {
            e.getLocalizedMessage();
        }
        return null;
    }

    @Override
    public Set<TransferenceResponseDTO> getAllTransferences() {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransferenceResponseDTO persistTransference(TransferenceRequestDTO transferenceRequestDTO, Account fromAccount, Account toAccount, BigDecimal commissionValue, BigDecimal value) {

        try {
            BigDecimal rate = new BigDecimal("0.02");
            Transference transference = new Transference();
            transference.setShippingCurrency(transferenceRequestDTO.getShippingCurrency());
            transference.setReceiptCurrency(transferenceRequestDTO.getReceiptCurrency());
            transference.setTransferValue(transferenceRequestDTO.getTransferValue());
            if (fromAccount.getUserId().equals(toAccount.getUserId())) {
                transference.setTransferType(TransferType.TRANSFER_TO_MY_ACCOUNT);
            } else {
                transference.setTransferType(TransferType.TRANSFER_TO_USER);
            }
            transference.setSenderUserId(fromAccount.getUserId());
            transference.setReceiverUserId(toAccount.getUserId());
            transference.setMethodOfPayment(MethodOfPayment.TRIWAL_TRANSFER);
            transference.setRate(rate);
            transference.setRateValue(commissionValue);
            transference.setTotalTransference(value);

            if (!transferenceRequestDTO.getShippingCurrency().name().equals(transferenceRequestDTO.getReceiptCurrency().name())) {
                transference.setTransactionDetails("Transacción con conversión");
            } else {
                transference.setTransactionDetails("Transacción misma moneda");
            }
            transference.setSourceAccountNumber(transferenceRequestDTO.getSourceAccountNumber());
            transference.setDestinationAccountNumber(transferenceRequestDTO.getDestinationAccountNumber());
            transference.setCreatedAt(new Date());

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            Transference transference1 = transferenceRepository.save(transference);
            return new TransferenceResponseDTO(transference1);
        } catch (Exception e){
            e.fillInStackTrace();
        }
        return null;
    }

    public BigDecimal calculateCommission(BigDecimal rate, BigDecimal value) {
        return value.multiply(rate);
    }

    public BigDecimal convertedTransference(BigDecimal totalTransaction, String originCurrency, String destinationCurrency) throws JsonProcessingException {
        String response;
        BigDecimal convertedResult;
        try {
            response = converterClient.convertCurrency(totalTransaction.doubleValue(), originCurrency, destinationCurrency);
            Thread.sleep(2000);
            String[] res = response.split(",");
            convertedResult = new BigDecimal(res[12].replaceAll("[^0-9.]", ""));
            System.out.println(convertedResult);
            return convertedResult;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal calculateTotalTransference(BigDecimal commissionValue, BigDecimal transferValue) {
        return transferValue.subtract(commissionValue); // todo: here i need the enhance the ux comission experience
    }

    public BigDecimal getNewTransferValue(BigDecimal rate, BigDecimal transferValue){
        BigDecimal commissionExtra = rate.add(BigDecimal.valueOf(1));
        return transferValue.multiply(commissionExtra);
    }

    public TransferenceInfoResponseDTO getTransferenceCost(BigDecimal rate, BigDecimal newValue, String originCurrency, String destinationCurrency ) throws JsonProcessingException {
        BigDecimal convertedValueToTransfer = null;
        BigDecimal commission = calculateCommission(rate, newValue);
        BigDecimal totalValueToTransfer = calculateTotalTransference(commission, newValue);
        if(!originCurrency.equals(destinationCurrency) && !originCurrency.isEmpty() && !destinationCurrency.isEmpty()){
           convertedValueToTransfer = convertedTransference(totalValueToTransfer,originCurrency,destinationCurrency);
        }
        TransferenceInfoResponseDTO transferenceCost = new TransferenceInfoResponseDTO();
        transferenceCost.setRate(String.valueOf(rate));
        transferenceCost.setCommissionValue(String.valueOf(commission));
        transferenceCost.setNewTransferValue(String.valueOf(totalValueToTransfer));
        transferenceCost.setConvertedNewTransferValue(String.valueOf(convertedValueToTransfer));
        return transferenceCost;
    }
}
