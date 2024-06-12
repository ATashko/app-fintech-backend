package com.mstransaction.mstransaction.controller;

import com.mstransaction.mstransaction.dto.DepositDTO;
import com.mstransaction.mstransaction.service.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3333/")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

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


}
