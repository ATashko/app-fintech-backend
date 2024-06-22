package com.mstransaction.mstransaction.domain;

import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;

@Data
@Entity
@Table(name = "transferences")
public class Transference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transferece_id")
    private Long transferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_currency",nullable = false)
    private ShippingCurrency shippingCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "receipt_currency",nullable = false)
    private ReceiptCurrency receiptCurrency;

    @Column(name = "transference_value",nullable = false)
    private BigDecimal transferValue;

    @Column(name = "commision_rate",nullable = false)
    private BigDecimal rate;

    @Column(name = "commision_value",nullable = false)
    private BigDecimal rateValue;

    @Column(name = "transference_total",precision = 10, scale = 3,nullable = false)
    private BigDecimal totalTransference;

    @Column(name = "from_user",nullable = false)
    private String senderUserId;

    @Column(name = "to_user",nullable = false)
    private String receiverUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_of_payment",nullable = false)
    private MethodOfPayment methodOfPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private TransferType transferType;

    @Column(name = "transaction_details")
    private String transactionDetails;

    @Column(name = "conversion_rate")
    private String conversionRate;

    @Column(name = "source_account_number",nullable = false)
    private String sourceAccountNumber;

    @Column(name = "destination_account_number",nullable = false)
    private String destinationAccountNumber;

    @Column(name = "transfered_at")
    private Date createdAt;

    @Column(name = "number_authorization", unique = true, nullable = false)
    private String autorizationNumber;

    @PrePersist
    public void generateAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(secureRandom.nextInt(10));
        }
        this.autorizationNumber= sb.toString();
    }
}
