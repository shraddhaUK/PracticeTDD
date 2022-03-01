package com.exchangeapi.api.blockchain.api;

import com.exchangeapi.api.blockchain.dto.BlockchainPriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
        name = "block-chain-currency-service",
        url = "${blockchain.url}")
public interface BlockchainFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/ticker")
    Map<String, BlockchainPriceDto> getCurrencies();
}
