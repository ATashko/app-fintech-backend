package com.mstransaction.mstransaction.repository;

import com.mstransaction.mstransaction.domain.Transference;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenceRepository extends JpaRepository<Transference, Long> {
	List<Transference> findAllBySenderUserId(String userId);
	List<Transference> findAllByReceiverUserId(String userId);
}
