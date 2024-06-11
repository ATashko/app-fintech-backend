package com.mstransaction.mstransaction.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mstransaction.mstransaction.domain.Account;
import com.mstransaction.mstransaction.dto.AccountDTO;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.service.IAccountService;

@Service
public class AccountService implements IAccountService {

	private final AccountRepository repositoryAccount;

	public AccountService(AccountRepository repositoryAccount) {
		this.repositoryAccount = repositoryAccount;
	}

	@Override
	public void createAccount(AccountDTO account) {
		Account accountE = new Account(account);
		this.repositoryAccount.save(accountE);
	}

	@Override
	public List<AccountDTO> getAccounts(String userId) {
		return this.repositoryAccount.findByUserId(userId)
				.stream()
				.map(AccountDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public AccountDTO getAccountDetail(String numberAccount) {
		AccountDTO accountDTO = new AccountDTO(repositoryAccount.findByAccountNumber(numberAccount));
		return accountDTO;
	}

	@Override
	public void deleteAccount(String numberAccount) {
		AccountDTO accountDTO = this.getAccountDetail(numberAccount);
		if(accountDTO.getAmount() == 0.0f) {
			repositoryAccount.deleteByAccountNumber(numberAccount);
		}
	}

}
