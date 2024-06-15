package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceRequestDTO {

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

