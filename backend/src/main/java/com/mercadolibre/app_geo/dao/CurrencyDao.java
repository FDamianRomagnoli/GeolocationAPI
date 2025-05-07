package com.mercadolibre.app_geo.dao;

import com.mercadolibre.app_geo.model.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CurrencyDao extends CrudRepository<CurrencyEntity, Long> {

    Optional<CurrencyEntity> findByCurrencyCode(String currencyCode);

}
