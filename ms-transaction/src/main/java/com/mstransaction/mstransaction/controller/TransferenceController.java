package com.mstransaction.mstransaction.controller;


import com.mstransaction.mstransaction.dto.TransferenceRequestDTO;
import com.mstransaction.mstransaction.dto.TransferenceResponseDTO;
import com.mstransaction.mstransaction.service.ITransferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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




}
