package com.mercadolibre.app_geo.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CurrencyDTO {
    String currencyCode;
    String currencySymbol;
    Map<String, Object> exchangeRates = new HashMap<>();
}
