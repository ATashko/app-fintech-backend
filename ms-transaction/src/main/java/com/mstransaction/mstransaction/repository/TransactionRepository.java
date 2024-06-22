package com.mstransaction.mstransaction.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mstransaction.mstransaction.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findFirstByAccountNumberOrderByTransactionIdDesc(String numberAccount);
    List<Transaction> findAllByUserId(String userId);

    List<Transaction> findAllByAccountNumber(String accountNumber);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.userId = :userId " +
            "AND (:fechaDesde IS NULL OR t.createdAt >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR t.createdAt <= :fechaHasta)")
     List<Transaction> findAllByUserIdAndDateRange(
             @Param("userId") String userId,
             @Param("fechaDesde") Date fechaDesde,
             @Param("fechaHasta") Date fechaHasta);

}
