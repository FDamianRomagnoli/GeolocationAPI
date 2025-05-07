package com.mercadolibre.app_geo.service.interfaces;

import java.time.ZonedDateTime;
import java.util.List;

public interface ExternalSourceTimeZoneDbApiService {

    public List<String> findAllTimeZoneByCountry(String countryCode);

}
