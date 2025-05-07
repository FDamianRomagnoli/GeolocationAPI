package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.model.RegionEntity;

public interface RegionService {

    RegionEntity findOrCreate(String regionName, Double latitude, Double longitude, CountryEntity country);

    RegionEntity findByRegionName(String regionName);

}
