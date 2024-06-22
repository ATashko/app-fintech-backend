package com.mstransaction.mstransaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.dto.*;

import java.math.BigDecimal;
import java.util.Set;

public interface ITransferenceService {

    TransferenceResponseDTO processTransference(TransferenceRequestDTO transactionDTO);

    Set<TransferenceResponseDTO> getAllTransferences();

    BigDecimal getNewTransferValue(BigDecimal rate, BigDecimal transferValue);

    TransferenceInfoResponseDTO getTransferenceCost(BigDecimal rate, BigDecimal newValue, String originCurrency, String destinationCurrency) throws JsonProcessingException;


    AccountMovementsResponseDTO getAccountMovementsByAccountNumber(MovementsRequestDTO movementsRequestDTO);
}
