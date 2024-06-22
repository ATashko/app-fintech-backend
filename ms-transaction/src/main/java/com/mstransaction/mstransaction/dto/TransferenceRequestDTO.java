package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceRequestDTO {

    private ShippingCurrency shippingCurrency;
    private ReceiptCurrency receiptCurrency;
    private BigDecimal transferValue;
    private String sourceAccountNumber;
    private String destinationAccountNumber;


}

