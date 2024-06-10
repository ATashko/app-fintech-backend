package com.mstransaction.mstransaction.domain;

import jakarta.persistence.*;

import lombok.*;


import java.io.Serializable;

@Data
@Entity
@Table(name = "transactions")

public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_currency", nullable = false)
    private ShippingCurrency shippingCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "receipt_currency", nullable = false)
    private ReceiptCurrency receiptCurrency;

    @Column(name = "value_to_transfer", nullable = false)
    private float valueToTransfer;

    @Column(name = "rate", nullable = false)
    private float rate;

    @Column(name = "rate_value", nullable = false)
    private float rateValue;

    @Column(name = "transaction_total", nullable = false)
    private float transactionTotal;

    @Column(name = "converted_transaction_total", nullable = false)
    private float convertedTransactionTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_of_payment", nullable = false)
    private MethodOfPayment methodOfPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type", nullable = false)
    private TransferType transferType;

    @Column(name = "name_of_recipient", nullable = false, length = 50)
    private String nameOfRecipient;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "transaction_details", nullable = false, length = 255)
    private String transactionDetails;

}
