package com.exchangeapi.api.exmo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExmoPriceDto {

    @JsonProperty("sell_price")
    private BigDecimal sellPrice;

}
