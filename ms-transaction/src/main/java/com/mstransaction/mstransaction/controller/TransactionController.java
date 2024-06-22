package com.mstransaction.mstransaction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.service.ITransactionService;

@CrossOrigin(origins = "http://localhost:3333")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final ITransactionService transactionService;

    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }


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



}
