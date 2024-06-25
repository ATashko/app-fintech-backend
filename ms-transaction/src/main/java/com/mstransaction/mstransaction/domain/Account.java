package com.mstransaction.mstransaction.domain;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;

import com.mstransaction.mstransaction.domain.enumTypes.Currency;
import com.mstransaction.mstransaction.domain.enumTypes.TypeAccount;
import com.mstransaction.mstransaction.dto.AccountDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER_ACCOUNT")
public class Account {

    @Id
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "amount",precision = 10, scale = 3)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @Column(name = "user_id")
    private String userId;

    /*@Column(name = "username")
    private String username;*/

    @Column(name = "name")
    private String name;
    
    @Column(name = "user_name")
	private String userName;

    /*@Column(name = "last_name")
    private String lastName;*/

    @Column(name = "created_at")
    private Date createdAt;

    public Account() {
    }

    public Account(AccountDTO accountDTO) {
        this.accountNumber = accountDTO.getAccountNumber();
        this.currency = Currency.valueOf(accountDTO.getCurrency());
        this.typeAccount = TypeAccount.valueOf(accountDTO.getTypeAccount());
        this.userId = accountDTO.getUserId();
        this.name = accountDTO.getName();
        this.userName = accountDTO.getUserName();
        this.createdAt = new Date();
        if (accountDTO.getAmount() != null) {
            this.amount = accountDTO.getAmount();
        } else {
            this.amount = BigDecimal.valueOf(0.000);
        }
    }

    @PrePersist
    public void generateAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(secureRandom.nextInt(10));
        }
        this.accountNumber = sb.toString();
    }

}
