package com.mercadolibre.app_geo.dao;

import com.mercadolibre.app_geo.model.CountryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CountryDao extends CrudRepository<CountryEntity, Long> {

    Optional<CountryEntity> findByNameAndIsoCode(String name, String isoCode);

}
