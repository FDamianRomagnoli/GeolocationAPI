package com.mercadolibre.app_geo.mapper;

import com.mercadolibre.app_geo.dto.RegionDTO;
import com.mercadolibre.app_geo.model.RegionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);



    @Mapping(source = "country.name", target = "countryName")
    RegionDTO toDto(RegionEntity region);

}
