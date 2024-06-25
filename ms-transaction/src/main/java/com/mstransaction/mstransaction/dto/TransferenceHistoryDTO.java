package com.mstransaction.mstransaction.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.domain.enumTypes.MethodOfPayment;
import com.mstransaction.mstransaction.domain.enumTypes.ReceiptCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.ShippingCurrency;
import com.mstransaction.mstransaction.domain.enumTypes.TransferType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TransferenceHistoryDTO {
    
	private Long historyId;
    private Date createAt;
    private String type;
    private ShippingCurrency shippingCurrency;
    private ReceiptCurrency receiptCurrency;
    private BigDecimal rateValue;
    private BigDecimal totalTransference;
    private BigDecimal transferValue;
    private String senderId;
    private String senderUserName;
    private String receiverId;
    private String receiverUserName;
    private MethodOfPayment methodOfPayment;
    private TransferType transferType;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    
	public TransferenceHistoryDTO(String type, Transference transference) {
		this.historyId = transference.getTransferenceId();
		this.createAt = transference.getCreatedAt();
		this.type = type;
		this.shippingCurrency = transference.getShippingCurrency();
		this.receiptCurrency = transference.getReceiptCurrency();
		this.rateValue = transference.getRateValue();
		this.totalTransference = transference.getTotalTransference();
		this.transferValue = transference.getTransferValue();
		this.senderId = transference.getSenderUserId();
		this.senderUserName = transference.getSenderUserName();
		this.receiverId = transference.getReceiverUserId();
		this.receiverUserName = transference.getReceiverUserName();
		this.methodOfPayment = transference.getMethodOfPayment();
		this.transferType = transference.getTransferType();
		this.sourceAccountNumber = transference.getSourceAccountNumber();
		this.destinationAccountNumber = transference.getDestinationAccountNumber();
	}

}

