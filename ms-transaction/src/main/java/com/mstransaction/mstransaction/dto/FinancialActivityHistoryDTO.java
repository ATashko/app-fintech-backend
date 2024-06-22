package com.mstransaction.mstransaction.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class FinancialActivityHistoryDTO {
    
    private Date date;
    private String type;
    private ShippingCurrency shippingCurrency;
    private ReceiptCurrency receiptCurrency;
    private BigDecimal rateValue;
    private BigDecimal totalTransference;
    private String senderId;
    private String receiverId;
    private MethodOfPayment methodOfPayment;
    private TransferType transferType;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    
	public FinancialActivityHistoryDTO(String type, Transaction transaction) {
		this.date = transaction.getCreatedAt();
		this.type = type;
		this.shippingCurrency = transaction.getShippingCurrency();
		this.receiptCurrency = transaction.getReceiptCurrency();
		this.rateValue = transaction.getRateValue();
		this.totalTransference = transaction.getConvertedTransactionTotal();
		this.methodOfPayment = transaction.getMethodOfPayment();
		this.transferType = transaction.getTransferType();
		this.destinationAccountNumber = transaction.getAccountNumber();
	}
    
	public FinancialActivityHistoryDTO(String type, Transference transference) {
		this.date = transference.getCreatedAt();
		this.type = type;
		this.shippingCurrency = transference.getShippingCurrency();
		this.receiptCurrency = transference.getReceiptCurrency();
		this.rateValue = transference.getRateValue();
		this.totalTransference = transference.getTotalTransference();
		this.senderId = transference.getSenderUserId();
		this.receiverId = transference.getReceiverUserId();
		this.methodOfPayment = transference.getMethodOfPayment();
		this.transferType = transference.getTransferType();
		this.sourceAccountNumber = transference.getSourceAccountNumber();
		this.destinationAccountNumber = transference.getDestinationAccountNumber();
	}

}

