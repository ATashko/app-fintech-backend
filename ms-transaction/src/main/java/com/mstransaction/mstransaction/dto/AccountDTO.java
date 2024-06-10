package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.Account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AccountDTO {
	private String accountNumber;
	private String currency;
	private String typeAccount;
	private String userId;
	
	public AccountDTO(Account account) {
		this.accountNumber = account.getAccountNumber();
	    this.currency = account.getCurrency().toString();
	    this.typeAccount = account.getTypeAccount().toString();
	    this.userId = account.getUserId();
	}
	
}
