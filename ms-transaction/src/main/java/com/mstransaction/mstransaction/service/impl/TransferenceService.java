package com.mstransaction.mstransaction.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.client.IConverterClient;
import com.mstransaction.mstransaction.domain.Account;
import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import com.mstransaction.mstransaction.dto.*;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransactionRepository;
import com.mstransaction.mstransaction.repository.TransferenceRepository;
import com.mstransaction.mstransaction.service.ITransferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Service
public class TransferenceService implements ITransferenceService {

    private final TransferenceRepository transferenceRepository;

    private final IConverterClient converterClient;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransferenceService(TransferenceRepository transferenceRepository, IConverterClient converterClient, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.transferenceRepository = transferenceRepository;
        this.converterClient = converterClient;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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
                BigDecimal rate = new BigDecimal("0.02");
                BigDecimal newValueTransfer = getNewTransferValue(rate, transferenceRequestDTO.getTransferValue());
                BigDecimal commissionValue = calculateCommission(rate, transferenceRequestDTO.getTransferValue());

                if (transferenceRequestDTO.getShippingCurrency().name().equals(transferenceRequestDTO.getReceiptCurrency().name())) {
                    sourceAccount.setAmount(sourceCurrentBalance.subtract(transferenceRequestDTO.getTransferValue()));
                    destinationAccount.setAmount(destinationCurrentBalance.add(transferenceRequestDTO.getTransferValue()));
                    return persistTransference(transferenceRequestDTO, sourceAccount, destinationAccount, BigDecimal.valueOf(0.00), transferenceRequestDTO.getTransferValue(), "0.00");
                } else {
                    sourceAccount.setAmount(sourceCurrentBalance.subtract(newValueTransfer));
                    BigDecimal totalTransference = calculateTotalTransference(commissionValue, newValueTransfer);
                    ConverterDTO convertedResponse = convertedTransference(totalTransference, transferenceRequestDTO.getShippingCurrency().toString(), transferenceRequestDTO.getReceiptCurrency().toString());
                    BigDecimal convertedValue = convertedResponse.getConversionResult();
                    String conversionRate = convertedResponse.getConversionRate();
                    destinationAccount.setAmount(destinationCurrentBalance.add(convertedValue));
                    return persistTransference(transferenceRequestDTO, sourceAccount, destinationAccount, commissionValue, convertedValue, conversionRate);
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
    public TransferenceResponseDTO persistTransference(TransferenceRequestDTO transferenceRequestDTO, Account fromAccount, Account toAccount, BigDecimal commissionValue, BigDecimal value, String conversionRate) {

        try {
            BigDecimal rate = new BigDecimal("0.02");
            Transference transference = new Transference();
            transference.setShippingCurrency(transferenceRequestDTO.getShippingCurrency());
            transference.setReceiptCurrency(transferenceRequestDTO.getReceiptCurrency());

            if (fromAccount.getUserId().equals(toAccount.getUserId())) {
                transference.setTransferType(TransferType.TRANSFER_TO_MY_ACCOUNT);
            } else {
                transference.setTransferType(TransferType.TRANSFER_TO_USER);
            }
            transference.setSenderUserId(fromAccount.getUserId());
            transference.setSenderUserName(fromAccount.getName());
            transference.setReceiverUserId(toAccount.getUserId());
            transference.setReceiverUserName(toAccount.getName());
            transference.setMethodOfPayment(MethodOfPayment.TRIWAL_TRANSFER);
            transference.setRateValue(commissionValue);
            transference.setTotalTransference(value);

            if (!transferenceRequestDTO.getShippingCurrency().name().equals(transferenceRequestDTO.getReceiptCurrency().name())) {
                transference.setTransactionDetails("Transacción con conversión");
                transference.setRate(rate);
                transference.setConversionRate(conversionRate);
                transference.setTransferValue(getNewTransferValue(rate, transferenceRequestDTO.getTransferValue()));
                transference.setNetTransferValue(transferenceRequestDTO.getTransferValue());
            } else {
                transference.setTransactionDetails("Transacción misma moneda");
                transference.setRate(BigDecimal.valueOf(0.00));
                transference.setConversionRate(conversionRate);
                transference.setTransferValue(transferenceRequestDTO.getTransferValue());
                transference.setNetTransferValue(transferenceRequestDTO.getTransferValue());
            }
            transference.setSourceAccountNumber(transferenceRequestDTO.getSourceAccountNumber());
            transference.setDestinationAccountNumber(transferenceRequestDTO.getDestinationAccountNumber());
            transference.setCreatedAt(new Date());

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            Transference transference1 = transferenceRepository.save(transference);
            return new TransferenceResponseDTO(transference1);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }


    public ConverterDTO convertedTransference(BigDecimal totalTransaction, String originCurrency, String destinationCurrency) throws JsonProcessingException {
        String response;
        String conversionRate;
        BigDecimal convertedResult;
        try {
            response = converterClient.convertCurrency(totalTransaction.doubleValue(), originCurrency, destinationCurrency);
            Thread.sleep(2000);
            String[] res = response.split(",");
            conversionRate = res[11].replaceAll("[^0-9.]", "");
            convertedResult = new BigDecimal(res[12].replaceAll("[^0-9.]", ""));
            ConverterDTO convertedResponse = new ConverterDTO();
            convertedResponse.setBaseCode(originCurrency);
            convertedResponse.setTargetCode(destinationCurrency);
            convertedResponse.setConversionRate(conversionRate);
            convertedResponse.setConversionResult(convertedResult);
            return convertedResponse;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal calculateCommission(BigDecimal rate, BigDecimal value) {
        return value.multiply(rate);
    }

    public BigDecimal calculateTotalTransference(BigDecimal commissionValue, BigDecimal transferValue) {
        return transferValue.subtract(commissionValue);
    }

    public BigDecimal getNewTransferValue(BigDecimal rate, BigDecimal transferValue) {
        BigDecimal commissionValue = calculateCommission(rate, transferValue);
        return transferValue.add(commissionValue);


    }

    public TransferenceInfoResponseDTO getTransferenceInfo(BigDecimal rate, BigDecimal transferValue, String originCurrency, String destinationCurrency) throws JsonProcessingException {
        TransferenceInfoResponseDTO transferenceInfo = new TransferenceInfoResponseDTO();
        BigDecimal convertedValueToTransfer;
        BigDecimal commissionValue = calculateCommission(rate, transferValue);
        BigDecimal newTransferValue = getNewTransferValue(rate, transferValue);

        if (!originCurrency.equals(destinationCurrency) && !originCurrency.isEmpty() && !destinationCurrency.isEmpty()) {
            ConverterDTO converterDTOResponse = convertedTransference(transferValue, originCurrency, destinationCurrency);

            if (converterDTOResponse != null) {
                convertedValueToTransfer = converterDTOResponse.getConversionResult();
                transferenceInfo.setConvertedTransferValue(convertedValueToTransfer);
                transferenceInfo.setConversionRate(converterDTOResponse.getConversionRate());
                transferenceInfo.setRate(rate);
                transferenceInfo.setCommissionValue(commissionValue);
                transferenceInfo.setTransferValue(transferValue);
                transferenceInfo.setNewTransferValue(newTransferValue);

            }
        } else {
            transferenceInfo.setConvertedTransferValue(transferValue);
            transferenceInfo.setRate(BigDecimal.valueOf(0.00));
            transferenceInfo.setConversionRate(String.valueOf(BigDecimal.valueOf(0.00)));
            transferenceInfo.setCommissionValue(BigDecimal.valueOf(0.00));
            transferenceInfo.setTransferValue(transferValue);
            transferenceInfo.setNewTransferValue(transferValue);
        }

        return transferenceInfo;
    }


    @Override
    public AccountMovementsResponseDTO getAccountMovementsByAccountNumber(MovementsRequestDTO movementsRequestDTO) {
        String accountNumber = movementsRequestDTO.getAccountNumber();
        String userId = movementsRequestDTO.getUserId();
        AccountMovementsResponseDTO accountMovementsResponseDTO = new AccountMovementsResponseDTO();
        List<Transaction> depositList = transactionRepository.findAllByAccountNumber(accountNumber);
        List<Transference> transferenceReceived = transferenceRepository.findReceivedTransference(accountNumber);
        List<Transference> transferenceSent = transferenceRepository.findSentTransference(accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber);

        accountMovementsResponseDTO.setAccountNumber(account.getAccountNumber());
        accountMovementsResponseDTO.setAmount(account.getAmount());
        accountMovementsResponseDTO.setTypeAccount(String.valueOf(account.getTypeAccount()));
        accountMovementsResponseDTO.setUserId(account.getUserId());
        accountMovementsResponseDTO.setCurrency(String.valueOf(account.getCurrency()));
        accountMovementsResponseDTO.setCreatedAt(account.getCreatedAt());

        accountMovementsResponseDTO.getTransferenceReceivedDTOList().addAll(transferenceReceived);
        accountMovementsResponseDTO.getTransferenceSendedDTOList().addAll(transferenceSent);
        accountMovementsResponseDTO.getDepositDTOList().addAll(depositList);

        return accountMovementsResponseDTO;
    }
}
