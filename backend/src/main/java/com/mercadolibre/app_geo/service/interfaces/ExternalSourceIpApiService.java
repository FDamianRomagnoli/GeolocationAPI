package com.mercadolibre.app_geo.service.interfaces;

import com.mercadolibre.app_geo.dto.IpApiResponse;

public interface ExternalSourceIpApiService {

    public IpApiResponse findDataByIp(String ip);
}
