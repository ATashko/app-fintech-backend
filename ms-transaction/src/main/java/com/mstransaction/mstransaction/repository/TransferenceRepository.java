package com.mstransaction.mstransaction.repository;

import com.mstransaction.mstransaction.domain.Transference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransferenceRepository extends JpaRepository<Transference, Long> {

    @Query("SELECT t FROM Transference t " +
            "WHERE (t.sourceAccountNumber = :accountNumber)" +
            "ORDER BY t.createdAt DESC")
    List<Transference> findSentTransference(@Param("accountNumber") String accountNumber);

    @Query("SELECT t FROM Transference t " +
            "WHERE (t.destinationAccountNumber = :accountNumber)" +
            "ORDER BY t.createdAt DESC")
    List<Transference> findReceivedTransference(@Param("accountNumber") String accountNumber);


    @Query("SELECT t FROM Transference t " +
            "WHERE (t.senderUserId = :userId) OR " +
            "(t.receiverUserId = :userId)" +
            "ORDER BY t.createdAt DESC")
    List<Transference> findAllTransferencesByUser(@Param("userId") String userId);


    @Query("SELECT t FROM Transference t " +
            "WHERE (t.senderUserId = :userId) OR " +
            "(t.receiverUserId = :userId) AND " +
            "(t.createdAt BETWEEN :startDate AND :endDate)" +
            "ORDER BY t.createdAt DESC")
    List<Transference> findAllTransferencesByUserIdBetweenDates(
            @Param("userId") String userId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT t FROM Transference t " +
            "WHERE (t.senderUserId = :userId) OR " +
            "(t.receiverUserId = :userId) AND " +
            "(t.sourceAccountNumber = :accountNumber) OR " +
            "(t.receiverUserId = :accountNumber) AND" +
            "(t.createdAt BETWEEN :startDate AND :endDate)" +
            "ORDER BY t.createdAt DESC")
    List<Transference> findAllTransferencesByUserIdBetweenDates(
            @Param("userId") String userId,
            @Param("accountNumber") String accountNumber,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
