package com.mstransaction.mstransaction.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConverterDTO {

    private String baseCode;
    private String targetCode;
    private float conversionRate;
    private float conversionResult;

}
