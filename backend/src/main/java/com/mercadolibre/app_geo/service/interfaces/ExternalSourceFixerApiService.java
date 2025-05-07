package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.model.CurrencyEntity;

import java.time.ZonedDateTime;

public interface ExternalSourceFixerApiService {

    public Double findConversionRateAndSave(String date, CurrencyEntity currencyFrom, CurrencyEntity currencyTo);
}
