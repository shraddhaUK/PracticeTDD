package com.exchangeapi.controller;

import com.exchangeapi.model.HighestPriceResponse;
import com.exchangeapi.service.BitcoinExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BitcoinExchangeController {

    @Autowired
    private BitcoinExchangeService bitcoinExchangeService;

    @RequestMapping(value = "/highestprice/{currency}", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public HighestPriceResponse getCurrencies(@PathVariable String currency) {
        return bitcoinExchangeService.getHighestPrice(currency);
    }
}
