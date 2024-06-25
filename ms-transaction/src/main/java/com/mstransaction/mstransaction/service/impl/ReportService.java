package com.mstransaction.mstransaction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.dto.DepositHistoryDTO;
import com.mstransaction.mstransaction.dto.TransferenceHistoryDTO;
import com.mstransaction.mstransaction.repository.TransactionRepository;
import com.mstransaction.mstransaction.repository.TransferenceRepository;
import com.mstransaction.mstransaction.service.IReportService;

@Service
public class ReportService implements IReportService {
	
	    private final TransactionRepository transactionRepository;

	    private final TransferenceRepository transferenceRepository;

	    public ReportService(TransactionRepository transactionRepository, TransferenceRepository transferenceRepository) {
			this.transactionRepository = transactionRepository;
			this.transferenceRepository = transferenceRepository;
		}
	    
	    @Override
		public List<Object> getTransactionHistoryByUserId(Date fechaDesde, Date fechaHasta, String userId) {
	        List<Transaction> transactions = transactionRepository.findAllByUserIdAndDateRange(userId, fechaDesde, fechaHasta);
	        List<Transference> transferencesSend = transferenceRepository.findAllSendByUserIdAndDateRange(userId, fechaDesde, fechaHasta);
	        List<Transference> transferencesReceive = transferenceRepository.findAllReceivedByUserIdAndDateRange(userId, fechaDesde, fechaHasta);

	        List<Object> history = new ArrayList<>();

	       for (Transaction transaction : transactions) {
	    	   DepositHistoryDTO dto = new DepositHistoryDTO("INCOME", transaction);
	            history.add(dto);
	        }

	        for (Transference transference : transferencesSend) {
	            TransferenceHistoryDTO senderDto = new TransferenceHistoryDTO("EXPENSE", transference);
	            history.add(senderDto);
	        }
	        
	        for (Transference transference : transferencesReceive) { 	
	            TransferenceHistoryDTO senderDto = new TransferenceHistoryDTO("INCOME", transference);
	            history.add(senderDto);
	        }
	        
	        //history.sort(Comparator.comparing(FinancialActivityHistoryDTO::getDate));
	        
	        return history;
	    }
}
