package com.mstransaction.mstransaction.service;

import java.util.Date;
import java.util.List;

public interface IReportService {
	List<Object> getTransactionHistoryByUserId(Date fechaDesde, Date fechaHasta, String userId);
}
