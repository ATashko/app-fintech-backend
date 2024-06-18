package com.mstransaction.mstransaction.service.impl;

import java.math.BigDecimal;
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
        return new AccountDTO(repositoryAccount.findByAccountNumber(numberAccount));
	}

	@Override
	public void deleteAccount(String numberAccount) {
		AccountDTO accountDTO = this.getAccountDetail(numberAccount);
		if(accountDTO.getAmount().compareTo(BigDecimal.ZERO) == 0.0) {
			repositoryAccount.deleteByAccountNumber(numberAccount);
		}
	}

}
