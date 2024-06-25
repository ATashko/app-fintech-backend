package com.mstransaction.mstransaction.domain;

import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "transactions")
public class Transaction  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private ShippingCurrency currency;

    @Column(name = "value_to_transfer",precision = 10, scale = 3)
    private BigDecimal valueToTransfer;


    @Enumerated(EnumType.STRING)
    @Column(name = "method_of_payment")
    private MethodOfPayment methodOfPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private TransferType transferType;

    @Column(name = "user_full_name", length = 50)
    private String userFullName;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "user_id",length = 36)
    private String userId;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "account_number")
    private String accountNumber;

}
