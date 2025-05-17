package com.mercadolibre.app_geo.controller;

import com.mercadolibre.app_geo.dto.ReportGeolocationDTO;
import com.mercadolibre.app_geo.service.GeolocationStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-geo/statistic")
public class StatisticController {

    @Autowired
    private GeolocationStatsService geolocationStatsService;

    @GetMapping("generateReport")
    public ReportGeolocationDTO generateReport(){
        return geolocationStatsService.generateReport();
    }


}
