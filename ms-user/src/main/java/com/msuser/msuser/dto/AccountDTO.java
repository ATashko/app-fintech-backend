package com.msuser.msuser.dto;

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
	private String amount;
	private String userId;
}