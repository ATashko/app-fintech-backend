package com.mstransaction.mstransaction.domain;

import com.mstransaction.mstransaction.dto.AccountDTO;
import jakarta.persistence.*;
import lombok.*;


import java.security.SecureRandom;
@Data
@Entity
@Table(name = "USER_ACCOUNT")
public class Account {
	
	@Id
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;
	
	@Column(name = "amount")
	private float amount;
	
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Enumerated(EnumType.STRING)
	private TypeAccount typeAccount;
	
	@Column(name = "user_id")
	private String userId;
	
    @PrePersist
    public void generateAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(secureRandom.nextInt(10));
        }
        this.accountNumber = sb.toString();
    }
    
    public Account(AccountDTO accountDTO) {
    	this.amount = 0.0f;
        this.currency = Currency.valueOf(accountDTO.getCurrency());
        this.typeAccount = TypeAccount.valueOf(accountDTO.getTypeAccount());
        this.userId = accountDTO.getUserId();
    }
}
