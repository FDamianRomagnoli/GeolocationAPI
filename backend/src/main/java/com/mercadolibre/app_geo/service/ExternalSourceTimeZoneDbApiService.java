package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dto.TimeZoneDbResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ExternalSourceTimeZoneDbApiService {

    private final RestTemplate restTemplate;

    private final ExternalSourceTimeZoneDbApiService self;

    @Value("${timezonedb.api.key}")
    private String apiKey;

    @Autowired
    public ExternalSourceTimeZoneDbApiService(
            RestTemplate restTemplate,
            @Lazy ExternalSourceTimeZoneDbApiService externalSourceTimeZoneDbApiService
    ){
        this.restTemplate = restTemplate;
        this.self = externalSourceTimeZoneDbApiService;
    }


    public List<String> findAllTimeZoneByCountry(String countryCode){

        ResponseEntity<TimeZoneDbResponse> response =  self.requestTimeZoneByCountryCode(countryCode);

        return generateTimeZoneBySource(response.getBody());
    }

    @Cacheable("countryTimeZones")
    public ResponseEntity<TimeZoneDbResponse> requestTimeZoneByCountryCode(String countryCode){

        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("api.timezonedb.com")
                .path("/v2.1/list-time-zone")
                .queryParam("key", apiKey)
                .queryParam("format", "json")
                .queryParam("country", countryCode)
                .build()
                .toUriString();

        return restTemplate.getForEntity(url, TimeZoneDbResponse.class);
    }

    private List<String> generateTimeZoneBySource(TimeZoneDbResponse source) {
        Instant now = Instant.now();

        Map<String, String> zoneTimes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (OOOO)");


        source.getZones().forEach(zone -> {
            ZonedDateTime time = ZonedDateTime.ofInstant(now, ZoneId.of(zone.getZoneName()));
            String timeFormatter = time.format(formatter);
            zoneTimes.put(timeFormatter, "");
        });

        return zoneTimes.keySet().stream().toList();
    }

}
