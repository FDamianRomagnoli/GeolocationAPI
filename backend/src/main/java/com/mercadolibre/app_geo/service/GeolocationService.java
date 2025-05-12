package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dto.*;
import com.mercadolibre.app_geo.exception.BusinessException;
import com.mercadolibre.app_geo.exception.messages.ErrorMessage;
import com.mercadolibre.app_geo.mapper.CountryMapper;
import com.mercadolibre.app_geo.mapper.CurrencyMapper;
import com.mercadolibre.app_geo.mapper.RegionMapper;
import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import com.mercadolibre.app_geo.model.RegionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class GeolocationService {

    private static final double EARTH_RADIUS_KM = 6371;
    private static final String REGION_DEFAULT = "Buenos Aires";
    private static final String CURRENCY_CODE_BASE_DEFAULT = "USD";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss (OOOO)");

    private final ExternalSourceIpApiService externalSourceIpApiService;

    private final CountryService countryService;

    private final RegionService regionService;

    private final ExchangeRateService exchangeRateService;
    private final GeolocationStatsService geolocationStatsService;

    private final ExternalSourceTimeZoneDbApiService externalSourceTimeZoneDbApiService;

    private final CurrencyService currencyService;

    private final CountryMapper countryMapper;

    private final CurrencyMapper currencyMapper;

    private final RegionMapper regionMapper;


    @Autowired
    public GeolocationService(
             ExternalSourceIpApiService externalSourceIpApiService,
             CountryService countryService,
             RegionService regionService,
             ExchangeRateService exchangeRateService,
             GeolocationStatsService geolocationStatsService,
             ExternalSourceTimeZoneDbApiService externalSourceTimeZoneDbApiService,
             CurrencyService currencyService,
             CountryMapper countryMapper,
             CurrencyMapper currencyMapper,
             RegionMapper regionMapper
    ){
        this.externalSourceIpApiService = externalSourceIpApiService;
        this.countryService = countryService;
        this.regionService = regionService;
        this.exchangeRateService = exchangeRateService;
        this.geolocationStatsService = geolocationStatsService;
        this.externalSourceTimeZoneDbApiService = externalSourceTimeZoneDbApiService;
        this.currencyService = currencyService;
        this.countryMapper = countryMapper;
        this.currencyMapper = currencyMapper;
        this.regionMapper = regionMapper;
    }


    public GeolocationDataDTO getGeolocationDataByIp(String ip) {
        IpApiResponse response = externalSourceIpApiService.findDataByIp(ip);
        geolocationStatsService.cleanCache();

        if(!hasCountryNameAndCode(response) && !hasLatitudeAndLongitude(response)){
            log.error("Api externa no devolvio correctamente datos claves como país y codigo");
            throw new BusinessException(ErrorMessage.IP_ERROR_API.getCode(), ErrorMessage.IP_ERROR_API.getMessage());
        }

        return generateGeolocationDataDtoFromSource(response);
    }


    private GeolocationDataDTO generateGeolocationDataDtoFromSource(IpApiResponse source) {
        GeolocationDataDTO dto = new GeolocationDataDTO();
        setCountryDtoFromSource(dto, source);
        setCurrencyDtoFromSource(dto, source);
        calculateDistanceToDefaultRegion(dto, source);
        return dto;
    }

    private void setCountryDtoFromSource(GeolocationDataDTO result, IpApiResponse source) {

        CountryEntity country = countryService.findOrCreate(source.getCountry_name(), source.getCountry_code());
        List<Map<String, Object>> languages = getLanguagesFromSource(source);
        List<String> timeZones = getTimeZonesFromSource(source);

        CountryDTO countryDto = countryMapper.INSTANCE.toDto(country);
        countryMapper.setLanguages(countryDto, languages);
        countryMapper.setCountryTimeZones(countryDto, timeZones);

        result.setCountryDTO(countryDto);
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

        if(source.getCurrency().getCode() != null){

            String currencyCode = source.getCurrency().getCode();
            CurrencyEntity currency = currencyService.findOrCreate(currencyCode);
            String symbol = null;

            if(source.getCurrency().getSymbol() != null){
                symbol = source.getCurrency().getSymbol();
            }

            String today = ZonedDateTime.now().format(DATE_FORMATTER);

            Double rate = exchangeRateService.findExchangeRateByDateAndCurrency(today, currencyCode, CURRENCY_CODE_BASE_DEFAULT);
            Map<String, Object> exchangeRates = new HashMap<>();
            exchangeRates.put(CURRENCY_CODE_BASE_DEFAULT, rate);

            CurrencyDTO currencyDTO = currencyMapper.INSTANCE.toDto(currency);
            currencyMapper.setSymbolAndExchangeRates(currencyDTO, symbol, exchangeRates);

            result.setCurrencyDTO(currencyDTO);

        }else{
            log.warn("Servicio no encontro codigo de moneda");
        }

    }

    private void calculateDistanceToDefaultRegion(GeolocationDataDTO result, IpApiResponse source) {
        CountryEntity country = countryService.findOrCreate(source.getCountry_name(), source.getCountry_code());
        RegionEntity region = regionService.findOrCreate(source.getRegion_name(), source.getLatitude(), source.getLongitude(), country);
        RegionEntity regionDefault = regionService.findByRegionName(REGION_DEFAULT);

        Double distance = calculateDistanceFromTo(region, regionDefault);

        result.setRegionFrom(regionMapper.INSTANCE.toDto(region));
        result.setRegionTo(regionMapper.INSTANCE.toDto(regionDefault));

        result.setDistanceInKmToBA(distance);

    }

    private Boolean hasCountryNameAndCode(IpApiResponse source){
        return source.getCountry_name() != null && source.getCountry_code() != null;
    }

    private Boolean hasLatitudeAndLongitude(IpApiResponse source){
        return source.getLongitude() != null && source.getLatitude() != null;
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

}