package com.mstransaction.mstransaction.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConverterDTO {

    private String baseCode;
    private String targetCode;
    private String conversionRate;
    private BigDecimal conversionResult;

}
