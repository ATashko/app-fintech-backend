package com.mstransaction.mstransaction.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AccountDTO {
	private String currency;
	private String typeAccount;
	private String userId;
}
