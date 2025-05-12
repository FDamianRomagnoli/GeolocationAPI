package com.mercadolibre.app_geo.mapper;

import com.mercadolibre.app_geo.dto.CountryDTO;
import com.mercadolibre.app_geo.model.CountryEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(source = "name", target = "countryName")
    @Mapping(source = "isoCode", target = "countryIsoCode")
    CountryDTO toDto(CountryEntity country);

    @Mapping(source = "name", target = "countryName")
    @Mapping(source = "isoCode", target = "countryIsoCode")
    @Mapping(source = "lenguajes", target = "lenguajes")
    @Mapping(source = "countryTimeZones", target = "countryTimeZones")
    CountryDTO toDto(CountryEntity country, List<Map<String, Object>> lenguajes, List<String> countryTimeZones);


}
