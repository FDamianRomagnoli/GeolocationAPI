package com.mercadolibre.app_geo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private String date;
    private Map<String, Double> rates;
}
