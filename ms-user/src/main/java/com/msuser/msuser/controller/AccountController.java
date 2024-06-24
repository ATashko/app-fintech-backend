package com.msuser.msuser.controller;

import java.util.List;

import com.msuser.msuser.configuration.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.msuser.msuser.client.IFeignClient;
import com.msuser.msuser.dto.AccountDTO;
import com.msuser.msuser.queue.AccountMessageSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = {"http://localhost:3333/", "https://triwal.tech"})
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountMessageSender accountMessageSender;
    private final IFeignClient feignClient;



    @Autowired
    public AccountController(AccountMessageSender accountMessageSender, IFeignClient feignClient, JwtAuthConverter jwtAuthConverter) {
        this.accountMessageSender = accountMessageSender;
        this.feignClient = feignClient;
        //this.jwtAuthConverter = jwtAuthConverter;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createAccount(@RequestBody AccountDTO accountDTO, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getClaimAsString("sub");

            String name = jwt.getClaimAsString("name");
            System.out.println(name);

            accountDTO.setUserId(userId);
            accountDTO.setName(name);

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
        return feignClient.getAccounts(userId);
    }

    @GetMapping("/{numberAccount}")
    public AccountDTO getAccountDetail(@PathVariable String numberAccount) {
        return feignClient.getAccountDetail(numberAccount);
    }

    @GetMapping(value = "/accountCertification", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateAccountPdf(@RequestParam String accountNumber){
        return feignClient.generateAccountPdf(accountNumber);
    }

    @DeleteMapping("/delete/{numberAccount}")
    public ResponseEntity<?> deleteAccount(@PathVariable String numberAccount) {
        feignClient.deleteAccount(numberAccount);
        return ResponseEntity.noContent().build();
    }
}
