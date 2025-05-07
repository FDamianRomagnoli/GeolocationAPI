package com.mercadolibre.app_geo.dao;

import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.model.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegionDao extends CrudRepository<RegionEntity, Long> {

    Optional<RegionEntity> findByRegionNameAndCountry(String regionName, CountryEntity country);

    Optional<RegionEntity> findByRegionName(String regionName);

}
