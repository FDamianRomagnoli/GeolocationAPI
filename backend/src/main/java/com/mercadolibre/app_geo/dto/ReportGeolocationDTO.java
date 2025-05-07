package com.mercadolibre.app_geo.dto;

import lombok.Data;

@Data
public class ReportGeolocationDTO {

    GeolocationStatsDTO geolocationMaxDistance;
    GeolocationStatsDTO geolocationMinDistance;
    Double averageDistance;

}
