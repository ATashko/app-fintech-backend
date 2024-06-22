package com.mstransaction.mstransaction.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mstransaction.mstransaction.domain.Transaction;
import com.mstransaction.mstransaction.domain.Transference;
import com.mstransaction.mstransaction.dto.FinancialActivityHistoryDTO;
import com.mstransaction.mstransaction.repository.TransactionRepository;
import com.mstransaction.mstransaction.repository.TransferenceRepository;
import com.mstransaction.mstransaction.service.IReportService;

@Service
public class ReportService implements IReportService {
	
	    private TransactionRepository transactionRepository;

	    private TransferenceRepository transferenceRepository;

	    public ReportService(TransactionRepository transactionRepository, TransferenceRepository transferenceRepository) {
			this.transactionRepository = transactionRepository;
			this.transferenceRepository = transferenceRepository;
		}
	    
	    @Override
		public List<FinancialActivityHistoryDTO> getTransactionHistoryByUserId(String userId) {
	        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
	        List<Transference> transferencesSend = transferenceRepository.findAllBySenderUserId(userId);
	        List<Transference> transferencesReceive = transferenceRepository.findAllByReceiverUserId(userId);

	        List<FinancialActivityHistoryDTO> history = new ArrayList<>();

	        for (Transaction transaction : transactions) {
	            FinancialActivityHistoryDTO dto = new FinancialActivityHistoryDTO("INCOME", transaction);
	            history.add(dto);
	        }

	        for (Transference transference : transferencesSend) { 	
	            FinancialActivityHistoryDTO senderDto = new FinancialActivityHistoryDTO("EXPENSE", transference);
	            history.add(senderDto);
	        }
	        
	        for (Transference transference : transferencesReceive) { 	
	            FinancialActivityHistoryDTO senderDto = new FinancialActivityHistoryDTO("INCOME", transference);
	            history.add(senderDto);
	        }
	        
	        history.sort(Comparator.comparing(FinancialActivityHistoryDTO::getDate));
	        
	        return history;
	    }
}
