package com.mercadolibre.app_geo.controller;

import com.mercadolibre.app_geo.dto.GeolocationDataDTO;
import com.mercadolibre.app_geo.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-geo/geolocation")

public class GeolocationController {

    @Autowired
    GeolocationService geolocationService;


    @GetMapping("findDataIp")
    public GeolocationDataDTO findDataIp(
            @RequestParam String ip
    ) {
        return geolocationService.getGeolocationDataByIp(ip);
    }

}
