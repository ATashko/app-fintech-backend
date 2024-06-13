package com.mstransaction.mstransaction.repository;

import com.mstransaction.mstransaction.domain.TransactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsAccountRepository extends JpaRepository<TransactionAccount, Long> {


}
