package com.mercadolibre.app_geo.dao;

import com.mercadolibre.app_geo.model.CurrencyEntity;
import com.mercadolibre.app_geo.model.ExchangeRateEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ExchangeRateDao extends CrudRepository<ExchangeRateEntity, Long> {

    List<ExchangeRateEntity> findByCurrencyFromAndCurrencyToAndConversionDate(CurrencyEntity currencyFrom, CurrencyEntity currencyTo, String date);

}
