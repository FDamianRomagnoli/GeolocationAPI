package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dto.ExchangeRateResponse;
import com.mercadolibre.app_geo.exception.BusinessException;
import com.mercadolibre.app_geo.exception.messages.ErrorMessage;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class ExternalSourceFixerApiService {

    private final String CURRENCY_BASE = "EUR";

    @Autowired
    protected RestTemplate restTemplate;

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
                .queryParam("base", CURRENCY_BASE)
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

        newRates.put(CURRENCY_BASE, base.equals(CURRENCY_BASE) ? 1.0 : 1.0 / baseRate);
        newRates.put(base, 1.0);

        return newRates;
    }

}
