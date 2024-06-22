package com.mstransaction.mstransaction.dto;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.domain.Transference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountMovementsResponseDTO {

    private String accountNumber;
    private String currency;
    private String typeAccount;
    private BigDecimal amount;
    private String userId;
    private Date createdAt;

    private final List<Transaction> depositDTOList = new ArrayList<>();
    private final List<Transference> transferenceSendedDTOList = new ArrayList<>();
    private final List<Transference> transferenceReceivedDTOList = new ArrayList<>();



}
