package com.msuser.msuser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.msuser.msuser.client.IAccountClient;
import com.msuser.msuser.dto.AccountDTO;
import com.msuser.msuser.queue.AccountMessageSender;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountMessageSender accountMessageSender;
    private final IAccountClient accountClient;

    @Autowired
    public AccountController(AccountMessageSender accountMessageSender, IAccountClient accountClient) {
        this.accountMessageSender = accountMessageSender;
		this.accountClient = accountClient;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO accountDTO, Authentication authentication) {
        try {
        	Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getClaimAsString("sub");
            
            accountDTO.setUserId(userId);
              
            accountMessageSender.sendCreateAccountMessage(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account creation message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error sending account creation message: " + e.getMessage());
        }
    }
    
    @GetMapping("/accounts")
    public List<AccountDTO> getAccountsByUserId(Authentication authentication) {
    	
    	Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString("sub");
        
        return accountClient.getAccounts(userId);
    }
    
    @GetMapping("/{numberAccount}")
    public AccountDTO getAccountDetail(@PathVariable @Valid String numberAccount) {     
        return accountClient.getAccountDetail(numberAccount);
    }
}

