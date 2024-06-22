package com.mstransaction.mstransaction.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mstransaction.mstransaction.dto.FinancialActivityHistoryDTO;
import com.mstransaction.mstransaction.service.impl.ReportService;
import com.mstransaction.mstransaction.util.ConfigHourDate;

@CrossOrigin(origins = "http://localhost:3333/")
@RestController
@RequestMapping("/report")
public class ReportController {
	
	private final ReportService serviceReport;
	private ConfigHourDate configHourDate = new ConfigHourDate();
	
	@Autowired
    public ReportController(ReportService serviceRepor) {
		this.serviceReport = serviceRepor;
    }
    
	@GetMapping("/all/{userId}")
    public ResponseEntity<List<FinancialActivityHistoryDTO>> getTransationsByUser(@PathVariable String userId, 
    		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
		
        if (fechaDesde != null) {
            fechaDesde = configHourDate.setHoraInicial(fechaDesde);
        }

        if (fechaHasta != null) {
            fechaHasta = configHourDate.setHoraFinal(fechaHasta);
        }
		
        List<FinancialActivityHistoryDTO> history = serviceReport.getTransactionHistoryByUserId(fechaDesde, fechaHasta, userId);
        
        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(history);
        }
    }
	
}
