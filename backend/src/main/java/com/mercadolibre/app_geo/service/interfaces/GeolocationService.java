package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.dto.GeolocationDataDTO;

public interface GeolocationService {

    GeolocationDataDTO getGeolocationDataByIp(String ip);

}
