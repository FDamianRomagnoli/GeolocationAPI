package com.mercadolibre.app_geo.service.impl;

import com.mercadolibre.app_geo.dto.ExchangeRateResponse;
import com.mercadolibre.app_geo.exception.BusinessException;
import com.mercadolibre.app_geo.exception.messages.ErrorMessage;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import com.mercadolibre.app_geo.service.interfaces.CurrencyService;
import com.mercadolibre.app_geo.service.interfaces.ExchangeRateService;
import com.mercadolibre.app_geo.service.interfaces.ExternalSourceFixerApiService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@Service
public class ExternalSourceFixerApiServiceImpl implements ExternalSourceFixerApiService {

    @Autowired
    protected RestTemplate restTemplate;

    @Lazy
    @Autowired
    private CurrencyService currencyService;

    @Lazy
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Value("${fixer.api.key}")
    private String apiKey;

    @Transactional
    public Double findConversionRateAndSave(String date, CurrencyEntity currencyFrom, CurrencyEntity currencyTo){

        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("data.fixer.io")
                .path("/api/" + date)
                .queryParam("access_key", apiKey)
                .queryParam("base", "EUR")
                .build()
                .toUriString();

        try{
            ResponseEntity<ExchangeRateResponse> response = restTemplate.getForEntity(url, ExchangeRateResponse.class);
            Map<String, Double> ratesEUR = response.getBody().getRates();

            ExchangeRateResponse exchangeRateResponse = response.getBody();

            exchangeRateResponse.setRates(transformRatesToBase(ratesEUR, currencyTo.getCurrencyCode()));
            exchangeRateResponse.setBase(currencyTo.getCurrencyCode());

            exchangeRateService.saveAllExchangeRateResponse(exchangeRateResponse);

            return exchangeRateResponse.getRates().get(currencyFrom.getCurrencyCode());

        } catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(ErrorMessage.EXCHANGE_RATE_ERROR_API.getCode(), ErrorMessage.EXCHANGE_RATE_ERROR_API.getMessage());
        }


    }


    private Map<String, Double> transformRatesToBase(Map<String, Double> rates, String base) {
        Double baseRate = rates.get(base);
        Map<String, Double> newRates = new HashMap<String, Double>();

        rates.forEach((key, value) -> {
            if (!key.equals(base)) {
                newRates.put(key, value / baseRate);
            }
        });

        newRates.put("EUR", base.equals("EUR") ? 1.0 : 1.0 / baseRate);
        newRates.put(base, 1.0);

        return newRates;
    }

}
