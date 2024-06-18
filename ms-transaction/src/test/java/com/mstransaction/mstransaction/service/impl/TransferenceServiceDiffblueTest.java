package com.mstransaction.mstransaction.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mstransaction.mstransaction.client.IConverterClient;
import com.mstransaction.mstransaction.repository.AccountRepository;
import com.mstransaction.mstransaction.repository.TransferenceRepository;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransferenceService.class})
@ExtendWith(SpringExtension.class)
class TransferenceServiceDiffblueTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private IConverterClient iConverterClient;

    @MockBean
    private TransferenceRepository transferenceRepository;

    @Autowired
    private TransferenceService transferenceService;

    /**
     * Method under test: {@link TransferenceService#convertedTransference(BigDecimal, String, String)}
     */
    @Test
    void testConvertedTransference() throws JsonProcessingException {
        when(iConverterClient.convertCurrency(anyDouble(), Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new IllegalArgumentException(","));
        assertThrows(IllegalArgumentException.class,
                () -> transferenceService.convertedTransference(new BigDecimal("2.3"), "GBP", "GBP"));
        verify(iConverterClient).convertCurrency(anyDouble(), Mockito.<String>any(), Mockito.<String>any());
    }
}
