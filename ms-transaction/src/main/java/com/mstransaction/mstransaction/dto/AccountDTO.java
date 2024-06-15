package com.mstransaction.mstransaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mstransaction.mstransaction.domain.Account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class AccountDTO {
	private String accountNumber;
	private String currency;
	private String typeAccount;
	private float amount;
	private String userId;
	private Date createdAt;

	public AccountDTO(Account account) {
		this.accountNumber = account.getAccountNumber();
		this.currency = account.getCurrency().toString();
		this.typeAccount = account.getTypeAccount().toString();
		this.amount = account.getAmount();
		this.userId = account.getUserId();
		this.createdAt = account.getCreatedAt();
	}

}