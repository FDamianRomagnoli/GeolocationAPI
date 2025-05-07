package com.mercadolibre.app_geo.dto;

import lombok.Data;

@Data
public class GeolocationStatsDTO {
    RegionDTO regionDtoFrom;
    RegionDTO regionDtoTo;
    Double distanceInKm;
    Long invocationCount;
}
