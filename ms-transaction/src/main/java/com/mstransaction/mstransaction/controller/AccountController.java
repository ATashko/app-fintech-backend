package com.mstransaction.mstransaction.controller;

import com.mstransaction.mstransaction.dto.AccountDTO;
import com.mstransaction.mstransaction.service.IAccountService;
import com.mstransaction.mstransaction.service.impl.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3333/")
@RestController
@RequestMapping("/account")
public class AccountController {
	
	private final IAccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/accounts/{userId}")
    public List<AccountDTO> getAccountsByUserId(@PathVariable String userId) {
        return accountService.getAccounts(userId);
    }
	
	@GetMapping("/{numberAccount}")
    public AccountDTO getAccountDetail(@PathVariable String numberAccount, Authentication authentication) {
		
    	Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString("sub");
        
        List<AccountDTO> accounts = getAccountsByUserId(userId);
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(numberAccount))
                .findFirst()
                .map(account -> accountService.getAccountDetail(numberAccount))
                .orElse(new AccountDTO());
    }
	
	@DeleteMapping("/delete/{numberAccount}")
    public void deleteAccount(@PathVariable String numberAccount) {
		accountService.deleteAccount(numberAccount);
    }
}
