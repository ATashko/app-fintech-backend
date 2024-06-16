package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.*;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceResponseDTO {

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

    public TransferenceResponseDTO(Transference transference) {
        this.shippingCurrency = ShippingCurrency.valueOf(transference.getShippingCurrency().toString());
        this.receiptCurrency = ReceiptCurrency.valueOf(transference.getReceiptCurrency().toString());
        this.valueToTransfer = transference.getValueToTransfer();
        this.rate = transference.getRate();
        this.rateValue = transference.getRateValue();
        this.transactionTotal = transference.getTransactionTotal();
        this.convertedTransactionTotal = transference.getConvertedTransactionTotal();
        this.methodOfPayment = MethodOfPayment.valueOf(transference.getMethodOfPayment().toString());
        this.transferType = TransferType.valueOf(transference.getTransferType().toString());
        this.sourceAccountNumber = transference.getSourceAccountNumber();
        this.destinationAccountNumber = transference.getDestinationAccountNumber();
    }
}
