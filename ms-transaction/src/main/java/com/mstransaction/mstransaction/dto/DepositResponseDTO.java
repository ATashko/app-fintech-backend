package com.mstransaction.mstransaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponseDTO {
    private String userId;
    private String accountNumber;
    private float valueToTransfer;
    private String shippingCurrency;
}