package com.mstransaction.mstransaction.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.dto.TransferenceInfoResponseDTO;
import com.mstransaction.mstransaction.dto.TransferenceRequestDTO;
import com.mstransaction.mstransaction.dto.TransferenceResponseDTO;
import com.mstransaction.mstransaction.service.ITransferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "http://localhost:3333")
@RestController
@RequestMapping("/transference")
public class TransferenceController {

    private final ITransferenceService transferenceService;

    public TransferenceController(ITransferenceService transferenceService) {
        this.transferenceService = transferenceService;
    }

    @PostMapping("/set")
    public ResponseEntity<TransferenceResponseDTO> setTransaction(@RequestBody TransferenceRequestDTO transferenceRequestDTO) {
        TransferenceResponseDTO transactionDTO = transferenceService.processTransference(transferenceRequestDTO);
        return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
    }

    @GetMapping("/info")
    public ResponseEntity<TransferenceInfoResponseDTO> calculateTransferenceCost(
            @RequestParam("value") String value,
            @RequestParam("originCurrency") String originCurrency,
            @RequestParam("destinationCurrency") String destinationCurrency) throws JsonProcessingException {

        BigDecimal rate = new BigDecimal("0.02");

        BigDecimal newTransferValue =
                transferenceService.getNewTransferValue(
                        rate,
                        new BigDecimal(value));
        TransferenceInfoResponseDTO transferenceCostInfo =
                transferenceService.getTransferenceCost(
                        rate,
                        newTransferValue,
                        originCurrency,
                        destinationCurrency
                );

        return new ResponseEntity<>(transferenceCostInfo, HttpStatus.ACCEPTED);

    }


}
