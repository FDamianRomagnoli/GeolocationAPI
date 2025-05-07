package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.dto.ExchangeRateResponse;
import com.mercadolibre.app_geo.model.ExchangeRateEntity;

import java.time.ZonedDateTime;

public interface ExchangeRateService {

    public Double findExchangeRateByDateAndCurrency(String date, String currencyCodeFrom, String currencyCodeTo);

    public void saveAllExchangeRateResponse(ExchangeRateResponse exchangeRateResponse);

}
