package com.mstransaction.mstransaction.repository;

import java.util.Date;
import java.util.List;

import com.mstransaction.mstransaction.domain.Transference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


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
    List<Transference> findAllTransferencesByUserIdAndAccountNumberBetweenDates(
            @Param("userId") String userId,
            @Param("accountNumber") String accountNumber,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT t FROM Transference t " +
            "WHERE t.senderUserId = :userId " +
            "AND (:fechaDesde IS NULL OR t.createdAt >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR t.createdAt <= :fechaHasta)")
     List<Transference> findAllSendByUserIdAndDateRange(
             @Param("userId") String userId,
             @Param("fechaDesde") Date fechaDesde,
             @Param("fechaHasta") Date fechaHasta);
    
    @Query("SELECT t FROM Transference t " +
            "WHERE t.receiverUserId = :userId " +
            "AND (:fechaDesde IS NULL OR t.createdAt >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR t.createdAt <= :fechaHasta)")
     List<Transference> findAllReceivedByUserIdAndDateRange(
             @Param("userId") String userId,
             @Param("fechaDesde") Date fechaDesde,
             @Param("fechaHasta") Date fechaHasta);

}
