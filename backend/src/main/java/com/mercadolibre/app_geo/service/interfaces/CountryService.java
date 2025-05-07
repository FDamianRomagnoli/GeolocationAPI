package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.model.CountryEntity;

public interface CountryService {

    CountryEntity findOrCreate(String countryName, String countryCode);

}
