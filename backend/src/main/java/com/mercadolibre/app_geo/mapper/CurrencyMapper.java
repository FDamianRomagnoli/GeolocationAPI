package com.mercadolibre.app_geo.mapper;

import com.mercadolibre.app_geo.dto.CurrencyDTO;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);


    CurrencyDTO toDto(CurrencyEntity currencyEntity);

    @AfterMapping
    default void setSymbolAndExchangeRates(@MappingTarget CurrencyDTO currencyDTO, String symbol, Map<String, Object> exchangeRates) {
        currencyDTO.setCurrencySymbol(symbol);
        currencyDTO.setExchangeRates(exchangeRates);
    }

}
