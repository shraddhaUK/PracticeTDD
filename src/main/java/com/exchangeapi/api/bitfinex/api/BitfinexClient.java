package com.exchangeapi.api.bitfinex.api;

import com.exchangeapi.api.bitfinex.dto.BitfinexPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
        name="bitfinex-currency-service",
        url="${bitfinex.url}"
)
public interface BitfinexClient {

    @RequestMapping(method = RequestMethod.GET , value = "/pubticker/btcusd")
    Map<String, BitfinexPriceDto> getCurrencies();
}
