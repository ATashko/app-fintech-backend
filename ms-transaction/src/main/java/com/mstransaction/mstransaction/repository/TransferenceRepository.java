package com.mstransaction.mstransaction.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mstransaction.mstransaction.domain.Transference;

public interface TransferenceRepository extends JpaRepository<Transference, Long> {
    @Query("SELECT t FROM Transference t " +
            "WHERE t.senderUserId = :userId " +
            "AND (:fechaDesde IS NULL OR t.createdAt >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR t.createdAt <= :fechaHasta)" +
            "ORDER BY t.sourceAccountNumber ASC")
     List<Transference> findAllSendByUserIdAndDateRange(
             @Param("userId") String userId,
             @Param("fechaDesde") Date fechaDesde,
             @Param("fechaHasta") Date fechaHasta);
    
    @Query("SELECT t FROM Transference t " +
            "WHERE t.receiverUserId = :userId " +
            "AND (:fechaDesde IS NULL OR t.createdAt >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR t.createdAt <= :fechaHasta)" +
            "ORDER BY t.destinationAccountNumber ASC")
     List<Transference> findAllReceivedByUserIdAndDateRange(
             @Param("userId") String userId,
             @Param("fechaDesde") Date fechaDesde,
             @Param("fechaHasta") Date fechaHasta);
}
