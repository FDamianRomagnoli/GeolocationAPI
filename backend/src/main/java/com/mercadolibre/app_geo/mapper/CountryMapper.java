package com.mercadolibre.app_geo.mapper;

import com.mercadolibre.app_geo.dto.CountryDTO;
import com.mercadolibre.app_geo.model.CountryEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(source = "name", target = "countryName")
    @Mapping(source = "isoCode", target = "countryIsoCode")
    CountryDTO toDto(CountryEntity country);

    @AfterMapping
    default void setLanguages(CountryDTO countryDTO, List<Map<String, Object>> languages){
        countryDTO.setCountryLanguages(languages);
    }

    @AfterMapping
    default void setCountryTimeZones(CountryDTO countryDTO, List<String> countryTimeZones){
        countryDTO.setCountryTimeZones(countryTimeZones);
    }


}
