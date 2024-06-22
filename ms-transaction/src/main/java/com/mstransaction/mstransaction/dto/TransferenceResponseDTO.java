package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.*;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import lombok.*;

import java.math.BigDecimal;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceResponseDTO {

    private ShippingCurrency shippingCurrency;
    private ReceiptCurrency receiptCurrency;
    private BigDecimal transferValue;
    private BigDecimal rate;
    private BigDecimal rateValue;
    private BigDecimal totalTransference;
    private String senderId;
    private String receiverId;
    private MethodOfPayment methodOfPayment;
    private TransferType transferType;
    private String conversionRate;
    private String sourceAccountNumber;
    private String destinationAccountNumber;

    public TransferenceResponseDTO(Transference transference) {
        this.shippingCurrency = ShippingCurrency.valueOf(String.valueOf(transference.getShippingCurrency()));
        this.receiptCurrency = ReceiptCurrency.valueOf(String.valueOf(transference.getReceiptCurrency()));
        this.transferValue = transference.getTransferValue();
        this.rate = transference.getRate();
        this.rateValue = transference.getRateValue();
        this.senderId = transference.getSenderUserId();
        this.receiverId = transference.getReceiverUserId();
        this.totalTransference = transference.getTotalTransference();
        this.methodOfPayment = MethodOfPayment.valueOf(transference.getMethodOfPayment().toString());
        this.transferType = TransferType.valueOf(transference.getTransferType().toString());
        this.conversionRate = transference.getConversionRate();
        this.sourceAccountNumber = transference.getSourceAccountNumber();
        this.destinationAccountNumber = transference.getDestinationAccountNumber();
    }
}
