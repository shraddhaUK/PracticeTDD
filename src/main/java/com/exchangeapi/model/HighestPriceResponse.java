package com.exchangeapi.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class HighestPriceResponse {

    private String apiName;

    private BigDecimal price;
}
