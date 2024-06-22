package com.mstransaction.mstransaction.service;

import java.util.List;

import com.mstransaction.mstransaction.dto.FinancialActivityHistoryDTO;

public interface IReportService {
	List<FinancialActivityHistoryDTO> getTransactionHistoryByUserId(String userId);
}
