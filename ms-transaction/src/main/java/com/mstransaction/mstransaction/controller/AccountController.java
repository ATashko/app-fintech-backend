package com.mstransaction.mstransaction.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstransaction.mstransaction.dto.AccountDTO;
import com.mstransaction.mstransaction.service.impl.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/accounts/{userId}")
    public List<AccountDTO> getAccountsByUserId(@PathVariable String userId) {
        return accountService.getAccounts(userId);
    }
	
	@GetMapping("/{numberAccount}")
    public AccountDTO getAccountDetail(@PathVariable String numberAccount, Authentication authentication) {
        return accountService.getAccountDetail(numberAccount);
    }
	
	@DeleteMapping("/delete/{numberAccount}")
    public void deleteAccount(@PathVariable String numberAccount) {
		accountService.deleteAccount(numberAccount);
    }
}
