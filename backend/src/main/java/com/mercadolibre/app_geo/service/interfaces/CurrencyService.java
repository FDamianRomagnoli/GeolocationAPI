package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.model.CurrencyEntity;

import java.util.List;

public interface CurrencyService {

    public CurrencyEntity findOrCreate(String currencyCode);



}
