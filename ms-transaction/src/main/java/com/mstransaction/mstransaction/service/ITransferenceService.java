package com.mstransaction.mstransaction.service;

import com.mstransaction.mstransaction.dto.TransferenceRequestDTO;
import com.mstransaction.mstransaction.dto.TransferenceResponseDTO;

import java.util.Set;

public interface ITransferenceService {

    TransferenceResponseDTO processTransference(TransferenceRequestDTO transactionDTO);

    Set<TransferenceResponseDTO> getAllTransferences();


}
