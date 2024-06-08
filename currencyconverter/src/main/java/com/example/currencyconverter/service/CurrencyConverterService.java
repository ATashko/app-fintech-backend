package com.example.currencyconverter.service;

import com.example.currencyconverter.model.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConverterService {

    @Value("${exchange.rates.api.key}")
    private String apiKey;

    @Value("${exchange.rates.api.url}")
    private String url;

    private final RestTemplate restTemplate;

    public CurrencyConverterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String convertCurrency(double amount, String fromCurrency, String toCurrency) {
        String baseUrl = url;
        String url = baseUrl + apiKey + "/pair/" + fromCurrency + "/" + toCurrency + "/" + amount;

        String jsonResponse = restTemplate.getForObject(url, String.class);
        System.out.println("Response JSON: " + jsonResponse);
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        return response.getConversion_result();
    }
}
