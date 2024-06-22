package com.mstransaction.mstransaction.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenceInfoResponseDTO {

    private String rate;
    private String commissionValue;
    private String newTransferValue;
    private String convertedNewTransferValue;


}
