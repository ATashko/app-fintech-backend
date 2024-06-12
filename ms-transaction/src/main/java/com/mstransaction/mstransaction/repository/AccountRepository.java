package com.mstransaction.mstransaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mstransaction.mstransaction.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	List<Account> findByUserId(String userId);
	Account findByAccountNumber(String numberAccount);
	void deleteByAccountNumber(String numberAccount);
}
