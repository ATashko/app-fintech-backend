package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.*;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    private ShippingCurrency shippingCurrency;
    private ReceiptCurrency receiptCurrency;
    private float valueToTransfer;
    private float rate;
    private float rateValue;
    private float transactionTotal;
    private float convertedTransactionTotal;
    private String userId;
    private String username;
    private String email;
    private String sourceAccountNumber;
    private String destinationAccountNumber;


}

