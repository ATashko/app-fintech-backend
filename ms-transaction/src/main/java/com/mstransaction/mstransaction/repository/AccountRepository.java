package com.mstransaction.mstransaction.repository;

import com.mstransaction.mstransaction.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String>{
    Optional<Account> findByUserId(String userId);
    Optional<Account> findByUserIdAndAccountNumber(String userId, String accountNumber);


}
