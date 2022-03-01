package com.exchangeapi.service;

import com.exchangeapi.api.bitfinex.api.BitfinexClient;
import com.exchangeapi.api.bitfinex.dto.BitfinexPriceDto;
import com.exchangeapi.api.blockchain.api.BlockchainFeignClient;
import com.exchangeapi.api.blockchain.dto.BlockchainPriceDto;
import com.exchangeapi.api.exmo.api.ExmoFeignClient;
import com.exchangeapi.api.exmo.dto.ExmoPriceDto;
import com.exchangeapi.common.Exchanges;
import com.exchangeapi.model.HighestPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class BitcoinExchangeServiceImpl implements BitcoinExchangeService {

    @Autowired
    private BlockchainFeignClient blockchainFeignClient;

    @Autowired
    private ExmoFeignClient exmoFeignClient;

    @Autowired
    private BitfinexClient bitfinexClient;

    @Override
    public HighestPriceResponse getHighestPrice(String currency) {
        Optional<BlockchainPriceDto> blockchainCurrencyPrice = getBlockchainPrice(currency);
        Optional<ExmoPriceDto> exmoCurrencyPrice = getExmoPrice(currency);
        Optional<BitfinexPriceDto> bitfinexCurrencyPrice = getBitfinexPrice(currency);

        List<HighestPriceResponse> responseFromAllExchanges = new ArrayList<HighestPriceResponse>();

        if(bitfinexCurrencyPrice.isPresent()){
            responseFromAllExchanges.add(HighestPriceResponse.builder().apiName(String.valueOf(Exchanges.Bitfinex)).price(new BigDecimal(bitfinexCurrencyPrice.get().getSellPrice())).build());
        }
        if (blockchainCurrencyPrice.isPresent()){
            responseFromAllExchanges.add(HighestPriceResponse.builder().apiName(String.valueOf(Exchanges.Blockchain)).price(blockchainCurrencyPrice.get().getSellPrice()).build());
        }
        if(exmoCurrencyPrice.isPresent()){
            responseFromAllExchanges.add(HighestPriceResponse.builder().apiName(String.valueOf(Exchanges.Exmo)).price(exmoCurrencyPrice.get().getSellPrice()).build());
        }
        if(!responseFromAllExchanges.isEmpty())
        return responseFromAllExchanges.stream().max((e1,e2) -> e1.getPrice().compareTo(e2.getPrice())).get();

        throw new IllegalStateException("USD currency not be found.");
    }

    private Optional<BitfinexPriceDto> getBitfinexPrice(String currency) {
        Map<String,BitfinexPriceDto> bitfinexCurrencies = bitfinexClient.getCurrencies();
        return Optional.ofNullable(bitfinexCurrencies.get("btc"+ currency.toLowerCase()));
    }

    private Optional<BlockchainPriceDto> getBlockchainPrice(String currency) {
        Map<String, BlockchainPriceDto> blockchainCurrencies = blockchainFeignClient.getCurrencies();
        return Optional.ofNullable(blockchainCurrencies.get(currency));
    }

    private Optional<ExmoPriceDto> getExmoPrice(String currency) {
        Map<String, ExmoPriceDto> exmoCurrencies = exmoFeignClient.getCurrencies();
        return Optional.ofNullable(exmoCurrencies.get("BTC_"+currency));
    }

}
