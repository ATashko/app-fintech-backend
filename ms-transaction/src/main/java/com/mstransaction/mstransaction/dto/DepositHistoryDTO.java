package com.mstransaction.mstransaction.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.mstransaction.mstransaction.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepositHistoryDTO {
	
	private Long historyId;
	private String type;
	private Date createAt;
    private String userId;
    private String accountNumber;
    private BigDecimal valueToTransfer;
    private String methodOfPayment;
    private String transferType;
    private String currency;
    private String email;
    private String username;
    private String userFullName;
    
	public DepositHistoryDTO(String type, Transaction transaction) {
		this.historyId = transaction.getTransactionId();
		this.type = type;
		this.createAt = transaction.getCreatedAt();
		this.userId = transaction.getUserId();
		this.accountNumber = transaction.getAccountNumber();
		this.valueToTransfer = transaction.getValueToTransfer();
		this.methodOfPayment = transaction.getMethodOfPayment().toString();
		this.transferType = transaction.getTransferType().toString();
		this.currency = transaction.getCurrency().toString();
		this.email = transaction.getEmail();
		this.username = transaction.getUsername();
		this.userFullName = transaction.getUserFullName();
	}
	
}
