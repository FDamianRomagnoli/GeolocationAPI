package com.mercadolibre.app_geo.dto;

import lombok.Data;

@Data
public class GeolocationDataDTO {

    CountryDTO countryDTO;

    CurrencyDTO currencyDTO;

    Double distanceInKmToBA;

    RegionDTO regionFrom;

    RegionDTO regionTo;

}
