package com.mercadolibre.app_geo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CountryDTO {
    String countryName;
    String countryIsoCode;
    List<Map<String, Object>> countryLanguages = new ArrayList<>();
    List<String> countryTimeZones = new ArrayList<>();
}
