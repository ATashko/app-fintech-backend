package com.mstransaction.mstransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mstransaction.mstransaction.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	
}
