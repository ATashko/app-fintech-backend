package com.mstransaction.mstransaction.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceInfoResponseDTO {

    private BigDecimal rate;
    private BigDecimal commissionValue;
    private BigDecimal transferValue;
    private BigDecimal newTransferValue;
    private String conversionRate;
    private BigDecimal convertedTransferValue;


}
