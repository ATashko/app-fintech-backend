package com.mstransaction.mstransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstransaction.mstransaction.dto.FinancialActivityHistoryDTO;
import com.mstransaction.mstransaction.service.impl.ReportService;

@CrossOrigin(origins = "http://localhost:3333/")
@RestController
@RequestMapping("/report")
public class ReportController {
	
	private final ReportService serviceReport;
	
	@Autowired
    public ReportController(ReportService serviceRepor) {
		this.serviceReport = serviceRepor;
    }
    
	@GetMapping("/all/{userId}")
    public ResponseEntity<List<FinancialActivityHistoryDTO>> getTransationsByUser(@PathVariable String userId) {
        List<FinancialActivityHistoryDTO> history = serviceReport.getTransactionHistoryByUserId(userId);
        
        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(history);
        }
    }
}
