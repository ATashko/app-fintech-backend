package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.*;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    private ShippingCurrency shippingCurrency;
    private ReceiptCurrency receiptCurrency;
    private float valueToTransfer;
    private float rate;
    private float rateValue;
    private float transactionTotal;
    private float convertedTransactionTotal;
    private MethodOfPayment methodOfPayment;
    private TransferType transferType;
    private String sourceAccountNumber;
    private String destinationAccountNumber;

    public TransactionResponseDTO(TransactionAccount transactionAccount) {
        this.shippingCurrency = ShippingCurrency.valueOf(transactionAccount.getShippingCurrency().toString());
        this.receiptCurrency = ReceiptCurrency.valueOf(transactionAccount.getReceiptCurrency().toString());
        this.valueToTransfer = transactionAccount.getValueToTransfer();
        this.rate = transactionAccount.getRate();
        this.rateValue = transactionAccount.getRateValue();
        this.transactionTotal = transactionAccount.getTransactionTotal();
        this.convertedTransactionTotal = transactionAccount.getConvertedTransactionTotal();
        this.methodOfPayment = MethodOfPayment.valueOf(transactionAccount.getMethodOfPayment().toString());
        this.transferType = TransferType.valueOf(transactionAccount.getTransferType().toString());
        this.sourceAccountNumber = transactionAccount.getSourceAccountNumber();
        this.destinationAccountNumber = transactionAccount.getDestinationAccountNumber();
    }
}
