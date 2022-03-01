package com.exchangeapi.api.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockchainPriceDto {

    @JsonProperty("sell")
    private BigDecimal sellPrice;

}
