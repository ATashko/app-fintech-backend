package com.msuser.msuser.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msuser.msuser.configuration.feign.FeignInterceptor;
import com.msuser.msuser.dto.AccountDTO;

@FeignClient(name = "ms-transaction", configuration = FeignInterceptor.class)
public interface IAccountClient {

    @GetMapping("/account/accounts/{userId}")
    List<AccountDTO> getAccounts(@PathVariable("userId") String userId);
    
    @GetMapping("/account/{numberAccount}")
    AccountDTO getAccountDetail(@PathVariable("numberAccount") String numberAccount);
    
    @DeleteMapping("/account/delete/{numberAccount}")
    void deleteAccount(@PathVariable("numberAccount") String numberAccount);
}