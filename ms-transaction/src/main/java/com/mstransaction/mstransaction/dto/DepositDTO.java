package com.mstransaction.mstransaction.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositDTO {

    private String userId;
    private String accountNumber;
    private BigDecimal valueToTransfer;
    private String shippingCurrency;
    private String email;
    private String username;
    private String userFullName;

}
