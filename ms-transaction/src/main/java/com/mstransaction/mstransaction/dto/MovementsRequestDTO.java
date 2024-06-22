package com.mstransaction.mstransaction.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementsRequestDTO {

    private String userId;
    private String accountNumber;
    private String initDate;
    private String finishDate;
}
