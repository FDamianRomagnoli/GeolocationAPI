package com.mercadolibre.app_geo.service.impl;

import com.mercadolibre.app_geo.dto.*;
import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.model.RegionEntity;
import com.mercadolibre.app_geo.service.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class GeolocationServiceImpl implements GeolocationService {

    private static final double EARTH_RADIUS_KM = 6371;
    private static final String REGION_DEFAULT = "Buenos Aires";
    private static final String CURRENCY_CODE_BASE_DEFAULT = "USD";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss (OOOO)");

    @Autowired
    private ExternalSourceIpApiServiceImpl externalSourceIpApiService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private GeolocationStatsService geolocationStatsService;

    @Autowired
    private ExternalSourceTimeZoneDbApiService externalSourceTimeZoneDbApiService;


    @Override
    public GeolocationDataDTO getGeolocationDataByIp(String ip) {
        try {
            IpApiResponse response = externalSourceIpApiService.findDataByIp(ip);
            geolocationStatsService.cleanCache();
            return generateGeolocationDataDtoFromSource(response);
        } catch (Exception e) {
            log.error("Error al obtener información de IpApi para IP {}: {}", ip, e.getMessage(), e);
            return null;
        }
    }


    private GeolocationDataDTO generateGeolocationDataDtoFromSource(IpApiResponse source) {
        GeolocationDataDTO dto = new GeolocationDataDTO();
        setCountryDtoFromSource(dto, source);
        setCurrencyDtoFromSource(dto, source);
        calculateDistanceToDefaultRegion(dto, source);
        return dto;
    }

    private void setCountryDtoFromSource(GeolocationDataDTO result, IpApiResponse source) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryName(source.getCountry_name());
        countryDTO.setCountryIsoCode(source.getCountry_code());

        countryDTO.setCountryTimeZones(getTimeZonesFromSource(source));
        countryDTO.setCountryLanguages(getLanguagesFromSource(source));

        result.setCountryDTO(countryDTO);
    }

    private List<String> getTimeZonesFromSource(IpApiResponse source) {
        try {
            return externalSourceTimeZoneDbApiService.findAllTimeZoneByCountry(source.getCountry_code());
        } catch (Exception e) {
            log.info("Error al obtener zonas horarias de TimeZoneDb, se usa IpApi");
            ZonedDateTime time = ZonedDateTime.now(ZoneId.of(source.getTime_zone().getId()));
            return Collections.singletonList(time.format(TIME_FORMATTER));
        }
    }

    private List<Map<String, Object>> getLanguagesFromSource(IpApiResponse source) {
        return source.getLocation().getLanguages().stream().map(language -> {
            Map<String, Object> lang = new HashMap<>();
            lang.put("name", language.getName());
            lang.put("code", language.getCode());
            return lang;
        }).toList();
    }

    private void setCurrencyDtoFromSource(GeolocationDataDTO result, IpApiResponse source) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setCurrencyCode(source.getCurrency().getCode());
        currencyDTO.setCurrencySymbol(source.getCurrency().getSymbol());

        String today = ZonedDateTime.now().format(DATE_FORMATTER);
        Double rate = exchangeRateService.findExchangeRateByDateAndCurrency(today, currencyDTO.getCurrencyCode(), CURRENCY_CODE_BASE_DEFAULT);

        Map<String, Object> exchange = new HashMap<>();
        exchange.put(CURRENCY_CODE_BASE_DEFAULT, rate);
        currencyDTO.setExchangeRates(exchange);

        result.setCurrencyDTO(currencyDTO);
    }

    private void calculateDistanceToDefaultRegion(GeolocationDataDTO result, IpApiResponse source) {
        CountryEntity country = countryService.findOrCreate(source.getCountry_name(), source.getCountry_code());
        RegionEntity region = regionService.findOrCreate(source.getRegion_name(), source.getLatitude(), source.getLongitude(), country);
        RegionEntity regionDefault = regionService.findByRegionName(REGION_DEFAULT);

        Double distance = calculateDistanceFromTo(region, regionDefault);

        result.setRegionFrom(regionEntityToRegionEntityDto(region, new RegionDTO()));
        result.setRegionTo(regionEntityToRegionEntityDto(regionDefault, new RegionDTO()));
        result.setDistanceInKmToBA(distance);
    }

    private Double calculateDistanceFromTo(RegionEntity region,RegionEntity regionDefault) {
        double distance = haversine(
                regionDefault.getLatitude(), regionDefault.getLongitude(),
                region.getLatitude(), region.getLongitude()
        );
        geolocationStatsService.incrementGeolocationStats(region, regionDefault, distance);
        return distance;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(Math.toRadians(lat1)) *
                        Math.cos(Math.toRadians(lat2)) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    private RegionDTO regionEntityToRegionEntityDto(RegionEntity source, RegionDTO dest){
        dest.setRegionName(source.getRegionName());
        dest.setCountryName(source.getCountry().getName());
        dest.setLongitude(source.getLongitude());
        dest.setLatitude(source.getLatitude());
        return dest;
    }
}