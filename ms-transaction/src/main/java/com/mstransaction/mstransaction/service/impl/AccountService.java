package com.mstransaction.mstransaction.service.impl;

import com.mstransaction.mstransaction.domain.Account;
import com.mstransaction.mstransaction.dto.AccountDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.service.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
	
	private final AccountRepository repositoryAccount;
	
	public AccountService(AccountRepository repositoryAccount) {
		super();
		this.repositoryAccount = repositoryAccount;
	}

	@Override
	public void createAccount(AccountDTO account) {
		Account accountE = new Account(account);
		this.repositoryAccount.save(accountE);
	}
	
}
