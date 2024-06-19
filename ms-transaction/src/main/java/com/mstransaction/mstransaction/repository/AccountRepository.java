package com.mstransaction.mstransaction.repository;

import com.mstransaction.mstransaction.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String>{

    Optional<Account> findByUserIdAndAccountNumber(String userId, String accountNumber);
    List<Account> findByUserId(String userId);
    Account findByAccountNumber(String numberAccount);
    void deleteByAccountNumber(String numberAccount);


}
