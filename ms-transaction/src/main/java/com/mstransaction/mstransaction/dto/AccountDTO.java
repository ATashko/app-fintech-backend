package com.mstransaction.mstransaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mstransaction.mstransaction.domain.Account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class AccountDTO {
	private String accountNumber;
	private String currency;
	private String typeAccount;
	private BigDecimal amount;
	private String userId;
	//private String username;
	private String name;
	//private String lastName;
	private Date createdAt;

	public AccountDTO(Account account) {
		this.accountNumber = account.getAccountNumber();
		this.currency = account.getCurrency().toString();
		this.typeAccount = account.getTypeAccount().toString();
		this.amount = account.getAmount();
		this.userId = account.getUserId();
		this.name = account.getName();
		this.createdAt = account.getCreatedAt();
	}

}