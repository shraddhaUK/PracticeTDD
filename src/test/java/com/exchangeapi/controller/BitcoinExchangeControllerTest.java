package com.exchangeapi.controller;

import com.exchangeapi.model.HighestPriceResponse;
import com.exchangeapi.service.BitcoinExchangeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(BitcoinExchangeController.class)
public class BitcoinExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BitcoinExchangeService bitcoinExchangeService;

    private HighestPriceResponse highestPriceResponse;

    @Test
    public void getHighestCurrencyShouldCallGetCurrenciesService() throws Exception {
        String currency ="USD";
        when(bitcoinExchangeService.getHighestPrice(currency)).thenReturn(highestPriceResponse);

        mockMvc.perform(get("/highestprice/")
                .contentType("application/json").accept("application/json"))
                .andExpect(status().isOk());

        verify(bitcoinExchangeService, VerificationModeFactory.times(1)).getHighestPrice(currency);
        verifyNoMoreInteractions(bitcoinExchangeService);
    }
}