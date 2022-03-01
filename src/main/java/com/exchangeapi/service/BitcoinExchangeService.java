package com.exchangeapi.service;

import com.exchangeapi.model.HighestPriceResponse;

public interface BitcoinExchangeService {

    HighestPriceResponse getHighestPrice(String currency);
}
