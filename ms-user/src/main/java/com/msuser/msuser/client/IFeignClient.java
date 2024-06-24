package com.msuser.msuser.client;

import com.msuser.msuser.configuration.feign.FeignInterceptor;
import com.msuser.msuser.dto.AccountDTO;
import com.msuser.msuser.dto.DepositDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@FeignClient(name = "ms-transaction", configuration = FeignInterceptor.class, url = "http://localhost:8088")
public interface IFeignClient {

    @GetMapping("/account/accounts/{userId}")
    List<AccountDTO> getAccounts(@PathVariable("userId") String userId);
    
    @GetMapping("/account/{numberAccount}")
    AccountDTO getAccountDetail(@PathVariable("numberAccount") String numberAccount);

    @GetMapping(value = "account/accountCertification", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateAccountPdf(@RequestParam String accountNumber);
    
    @DeleteMapping("/account/delete/{numberAccount}")
    void deleteAccount(@PathVariable("numberAccount") String numberAccount);

    @GetMapping("/transactions/deposit/{accountNumber}")
    ResponseEntity<DepositDTO> getDepositDetail(@PathVariable String accountNumber);

    @PostMapping("/transactions/deposit/")
    ResponseEntity<DepositDTO> getDepositSaving(@RequestBody DepositDTO depositRequest);

    @GetMapping("/transactions/all/{userId}")
    public ResponseEntity<List<?>> getAll(@PathVariable String userId);

}