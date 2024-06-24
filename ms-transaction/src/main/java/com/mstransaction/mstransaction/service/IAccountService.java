package com.mstransaction.mstransaction.service;

import java.util.List;

import com.mstransaction.mstransaction.dto.AccountDTO;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.AccountNotFoundException;

public interface IAccountService {
	void createAccount(AccountDTO account);
	List<AccountDTO> getAccounts(String userId);
	AccountDTO getAccountDetail(String userId);
	void deleteAccount(String numberAccount);
	public byte[] generateAccountPdfAsBytes(String accountNumber);
}