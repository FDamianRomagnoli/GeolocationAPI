package com.mercadolibre.app_geo.service.impl;

import com.mercadolibre.app_geo.dao.ExchangeRateDao;
import com.mercadolibre.app_geo.dto.ExchangeRateResponse;
import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import com.mercadolibre.app_geo.model.ExchangeRateEntity;
import com.mercadolibre.app_geo.service.interfaces.CurrencyService;
import com.mercadolibre.app_geo.service.interfaces.ExchangeRateService;
import com.mercadolibre.app_geo.service.interfaces.ExternalSourceFixerApiService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateDao exchangeRateDao;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ExternalSourceFixerApiService externalSourceFixerApiService;

    @Override
    @Transactional
    public Double findExchangeRateByDateAndCurrency(String date, String currencyCodeFrom, String currencyCodeTo) {

        CurrencyEntity currencyFrom = currencyService.findOrCreate(currencyCodeFrom);
        CurrencyEntity currencyTo = currencyService.findOrCreate(currencyCodeTo);

        Double conversionRate = findConversionRateInLocal(date, currencyFrom, currencyTo, true);

        if(conversionRate == null){
            log.info("No se encontro conversion guardada en la base de datos, se consulta a fuentes externas");
            return externalSourceFixerApiService.findConversionRateAndSave(date, currencyFrom, currencyTo);
        }

        return conversionRate;

    }

    @Override
    @Transactional
    public void saveAllExchangeRateResponse(ExchangeRateResponse exchangeRateResponse) {

        String currencyCodeTo = exchangeRateResponse.getBase();
        Map<String, Double> rates = exchangeRateResponse.getRates();

        CurrencyEntity currencyTo = currencyService.findOrCreate(currencyCodeTo);

        List<ExchangeRateEntity> exchangeRateEntities = new ArrayList<>();

        rates.forEach((key, value) -> {
            ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity();
            exchangeRateEntity.setConversionDate(exchangeRateResponse.getDate());
            exchangeRateEntity.setCurrencyTo(currencyTo);

            CurrencyEntity currencyFrom = currencyService.findOrCreate(key);

            exchangeRateEntity.setCurrencyFrom(currencyFrom);

            exchangeRateEntity.setConversionRate(value);

            exchangeRateEntities.add(exchangeRateEntity);

        });

        log.info("Guardando tasas de cambio para la fecha {}", exchangeRateResponse.getDate());
        exchangeRateDao.saveAll(exchangeRateEntities);

    }

    private Double findConversionRateInLocal(String date, CurrencyEntity currencyFrom, CurrencyEntity currencyTo, boolean tryInverse){


        List<ExchangeRateEntity> exchangeRateEntityList =
                exchangeRateDao.findByCurrencyFromAndCurrencyToAndConversionDate(currencyFrom, currencyTo, date);

        if(exchangeRateEntityList.isEmpty()) {
            if(tryInverse){
                return findConversionRateInLocal(date, currencyTo, currencyFrom, false);
            }

            return null;
        }
        else{
            Double conversionRate = exchangeRateEntityList.get(0).getConversionRate();
            return tryInverse ? conversionRate : 1 / conversionRate;
        }
    }
}
