package com.mercadolibre.app_geo.mapper;

import com.mercadolibre.app_geo.dto.CurrencyDTO;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    @Mapping(source = "symbol", target = "currencySymbol")
    @Mapping(source = "exchangeRates", target = "exchangeRates")
    CurrencyDTO toDto(CurrencyEntity currencyEntity, String symbol, Map<String, Object> exchangeRates);

}
