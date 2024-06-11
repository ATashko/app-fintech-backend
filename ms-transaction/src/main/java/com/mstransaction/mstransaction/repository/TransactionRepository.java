package com.mstransaction.mstransaction.repository;

import com.mstransaction.mstransaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findFirstByAccountNumberOrderByTransactionIdDesc(String numberAccount);
}
