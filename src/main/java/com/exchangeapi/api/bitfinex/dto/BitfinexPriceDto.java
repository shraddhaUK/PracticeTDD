package com.exchangeapi.api.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BitfinexPriceDto {

    @JsonProperty("ask")
    private String sellPrice;
}
