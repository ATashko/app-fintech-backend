package com.mstransaction.mstransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mstransaction.mstransaction.client.IConverterClient;

@RestController
@RequestMapping("/convert")
public class ConverterController {
	

	private final IConverterClient converterClient;
	
    public ConverterController(IConverterClient converterClient) {
		this.converterClient = converterClient;
	}

	@GetMapping
    public String convertCurrency(@RequestParam double amount, @RequestParam String from, @RequestParam String to) {
        return converterClient.convertCurrency(amount, from, to);
    }

}
