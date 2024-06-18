package com.example.currencyconverter.controller;

import com.example.currencyconverter.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3333/","https://triwal.tech" })
@RestController
public class CurrencyConverterController {


    private final CurrencyConverterService currencyConverterService;

    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/convert")
    public String convertCurrency(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to) {
        return currencyConverterService.convertCurrency(amount, from, to);
    }
}
