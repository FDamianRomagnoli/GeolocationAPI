package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.dto.GeolocationDataDTO;
import com.mercadolibre.app_geo.dto.GeolocationStatsDTO;
import com.mercadolibre.app_geo.dto.ReportGeolocationDTO;
import com.mercadolibre.app_geo.model.RegionEntity;

public interface GeolocationStatsService {
    public void incrementGeolocationStats(RegionEntity regionFrom, RegionEntity regionTo, Double distance);

    public ReportGeolocationDTO generateReport();

    public void cleanCache();
}
