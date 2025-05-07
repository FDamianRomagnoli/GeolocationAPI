package com.mercadolibre.app_geo.dto;

import lombok.Data;

@Data
public class RegionDTO {
    String regionName;
    String countryName;
    Double latitude;
    Double longitude;
}
