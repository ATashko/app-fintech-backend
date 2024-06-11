package com.msuser.msuser.client;

import com.msuser.msuser.configuration.feign.FeignInterceptor;
import com.msuser.msuser.dto.AccountDTO;
import com.msuser.msuser.dto.DepositDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-transaction", configuration = FeignInterceptor.class)
public interface IFeignClient {

    @GetMapping("/account/accounts/{userId}")
    List<AccountDTO> getAccounts(@PathVariable("userId") String userId);
    
    @GetMapping("/account/{numberAccount}")
    AccountDTO getAccountDetail(@PathVariable("numberAccount") String numberAccount);
    
    @DeleteMapping("/account/delete/{numberAccount}")
    void deleteAccount(@PathVariable("numberAccount") String numberAccount);

    @GetMapping("/transactions/deposit/{accountNumber}")
    ResponseEntity<DepositDTO> getDepositDetail(@PathVariable String accountNumber);
}