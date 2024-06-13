package com.mstransaction.mstransaction.controller;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.dto.TransactionRequestDTO;
import com.mstransaction.mstransaction.dto.TransactionResponseDTO;
import com.mstransaction.mstransaction.service.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3333")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<?>> getAll(@PathVariable String userId) {
        try {
            List<Transaction> transactions =  transactionService.getAllTransactions(userId);

            System.out.println(transactions);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deposit/{accountNumber}")
    public ResponseEntity<DepositDTO> getDepositDetail(@PathVariable String accountNumber) {
        try {
            DepositDTO depositDetail = transactionService.getDepositDetail(accountNumber);
            String account = depositDetail.getAccountNumber();
            System.out.println(account);
            return new ResponseEntity<>(depositDetail, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deposit/")
    public ResponseEntity<DepositDTO> setDepositSaving(@RequestBody DepositDTO depositRequest) {
        System.out.println("*******************");
        System.out.println(depositRequest.getAccountNumber());
        try {
            DepositDTO depositDetail = transactionService.processDeposit(depositRequest);
            System.out.println(depositDetail.getAccountNumber());
            return new ResponseEntity<>(depositDetail, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setTransaction")
    public ResponseEntity<TransactionResponseDTO> setTransaction(@RequestBody TransactionRequestDTO transactionRequest) {
        TransactionResponseDTO transactionDTO = transactionService.proccessTransaction(transactionRequest);
            return new ResponseEntity<>(transactionDTO,HttpStatus.CREATED);
    }


}
