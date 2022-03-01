package com.exchangeapi.service;


import com.exchangeapi.api.bitfinex.api.BitfinexClient;
import com.exchangeapi.api.bitfinex.dto.BitfinexPriceDto;
import com.exchangeapi.api.blockchain.api.BlockchainFeignClient;
import com.exchangeapi.api.blockchain.dto.BlockchainPriceDto;
import com.exchangeapi.api.exmo.api.ExmoFeignClient;
import com.exchangeapi.api.exmo.dto.ExmoPriceDto;
import com.exchangeapi.model.HighestPriceResponse;

import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitcoinExchangeServiceTest {

    @Autowired
    private BitcoinExchangeService bitcoinExchangeService;

    @MockBean
    private BlockchainFeignClient blockchainFeignClient;

    @MockBean
    private ExmoFeignClient exmoFeignClient;

    @MockBean
    private BitfinexClient bitfinexClient;
    private String currency = "USD";

    @Test
    public void givenBlockchainHasTheHigherPrice_ThenBlockchainIsReturnedAsTheHighestPrice() {
        when(blockchainFeignClient.getCurrencies()).thenReturn(blockchainPriceOf(BigDecimal.valueOf(12999.99)));
        when(exmoFeignClient.getCurrencies()).thenReturn(exmoPriceOf(BigDecimal.valueOf(11999.99)));

        HighestPriceResponse highestPrice = bitcoinExchangeService.getHighestPrice(currency);

        assertThat(highestPrice.getPrice()).isEqualTo(BigDecimal.valueOf(12999.99));
        assertThat(highestPrice.getApiName()).isEqualTo("Blockchain");
    }

    @Test
    public void givenExmoHasTheHigherPrice_ThenExmoIsReturnedAsTheHighestPrice() {
        when(blockchainFeignClient.getCurrencies()).thenReturn(blockchainPriceOf(BigDecimal.valueOf(12999.99)));
        when(exmoFeignClient.getCurrencies()).thenReturn(exmoPriceOf(BigDecimal.valueOf(13999.99)));

        HighestPriceResponse highestPrice = bitcoinExchangeService.getHighestPrice(currency);

        assertThat(highestPrice.getPrice()).isEqualTo(BigDecimal.valueOf(13999.99));
        assertThat(highestPrice.getApiName()).isEqualTo("Exmo");
    }

    @Test
    public void givenBitfinexHasTheHigherPrice_ThenBitfinexIsReturnedAsTheHighestPrice() {
        when(bitfinexClient.getCurrencies()).thenReturn(bitfinexPriceOf("15001.0"));
        when(blockchainFeignClient.getCurrencies()).thenReturn(blockchainPriceOf(BigDecimal.valueOf(12999.99)));
        when(exmoFeignClient.getCurrencies()).thenReturn(exmoPriceOf(BigDecimal.valueOf(13999.99)));

        HighestPriceResponse highestPrice = bitcoinExchangeService.getHighestPrice(currency);

        assertThat(highestPrice.getPrice()).isEqualTo(BigDecimal.valueOf(15001.0));
        assertThat(highestPrice.getApiName()).isEqualTo("Bitfinex");
    }

    @Test
    public void givenUsdPriceCantBeFound_ThenAnExceptionIsThrown() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bitcoinExchangeService.getHighestPrice(currency);
        });
    }

    private Map<String, BlockchainPriceDto> blockchainPriceOf(BigDecimal price) {
        return Maps.newHashMap("USD", new BlockchainPriceDto(price));
    }

    private Map<String, ExmoPriceDto> exmoPriceOf(BigDecimal price) {
        return Maps.newHashMap("BTC_USD", new ExmoPriceDto(price));
    }

    private Map<String, BitfinexPriceDto> bitfinexPriceOf(String price) {
        return Maps.newHashMap("btcusd", new BitfinexPriceDto(price));
    }
}

