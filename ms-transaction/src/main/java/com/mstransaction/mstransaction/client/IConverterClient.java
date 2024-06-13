package com.mstransaction.mstransaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "currencyconverter", url = "http://localhost:8083")
public interface IConverterClient {

    @GetMapping("/convert")
    String convertCurrency(@RequestParam double amount, @RequestParam String from, @RequestParam String to);
}


