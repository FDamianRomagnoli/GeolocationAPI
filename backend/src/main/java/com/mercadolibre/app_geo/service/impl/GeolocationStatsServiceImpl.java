package com.mercadolibre.app_geo.service.impl;

import com.mercadolibre.app_geo.dao.GeolocationStatsDao;
import com.mercadolibre.app_geo.dto.GeolocationDataDTO;
import com.mercadolibre.app_geo.dto.GeolocationStatsDTO;
import com.mercadolibre.app_geo.dto.RegionDTO;
import com.mercadolibre.app_geo.dto.ReportGeolocationDTO;
import com.mercadolibre.app_geo.model.GeolocationStatsEntity;
import com.mercadolibre.app_geo.model.RegionEntity;
import com.mercadolibre.app_geo.service.interfaces.GeolocationStatsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
public class GeolocationStatsServiceImpl implements GeolocationStatsService {

    @Autowired
    GeolocationStatsDao geolocationStatsDao;

    @Transactional
    public void incrementGeolocationStats(RegionEntity regionFrom, RegionEntity regionTo, Double distance) {
        GeolocationStatsEntity geolocationStats = findOrCreate(regionFrom, regionTo, distance);
        geolocationStats.setInvocationCount(geolocationStats.getInvocationCount() + 1);
    }

    @Override
    @Transactional
    @Cacheable(value = "report")
    public ReportGeolocationDTO generateReport() {
        ReportGeolocationDTO reportGeolocationDTO = new ReportGeolocationDTO();

        reportGeolocationDTO.setAverageDistance(geolocationStatsDao.calculateAbs());

        reportGeolocationDTO.setGeolocationMinDistance(
                geolocationEntityToGeolocationEntityDto(geolocationStatsDao.findByMinDistance(), new GeolocationStatsDTO())
        );

        reportGeolocationDTO.setGeolocationMaxDistance(
                geolocationEntityToGeolocationEntityDto(geolocationStatsDao.findByMaxDistance(), new GeolocationStatsDTO())
        );


        return reportGeolocationDTO;
    }

    @CacheEvict(value = "report", allEntries = true)
    public void cleanCache(){
        log.info("Limpiando cache de reporte");
    }

    @Transactional
    protected GeolocationStatsEntity findOrCreate(RegionEntity regionFrom, RegionEntity regionTo, Double distance){
        Optional<GeolocationStatsEntity> geolocationStatsEntityOpt =
                geolocationStatsDao.findByRegionFromAndRegionTo(regionFrom, regionTo);

        if(geolocationStatsEntityOpt.isEmpty()){
            geolocationStatsEntityOpt = geolocationStatsDao.findByRegionFromAndRegionTo(regionTo, regionFrom);
        }

        if(geolocationStatsEntityOpt.isEmpty()){
            GeolocationStatsEntity geolocationStatsEntity = new GeolocationStatsEntity();
            geolocationStatsEntity.setRegionFrom(regionFrom);
            geolocationStatsEntity.setRegionTo(regionTo);
            geolocationStatsEntity.setDistanceKm(distance);
            geolocationStatsEntity.setInvocationCount(0);
            geolocationStatsEntity.setCreateDate(ZonedDateTime.now());

            return geolocationStatsDao.save(geolocationStatsEntity);
        }
        else{
            return geolocationStatsEntityOpt.get();
        }
    }

    private GeolocationStatsDTO geolocationEntityToGeolocationEntityDto(GeolocationStatsEntity source, GeolocationStatsDTO dest){
        dest.setRegionDtoTo(regionEntityToRegionEntityDto(source.getRegionTo(), new RegionDTO()));
        dest.setRegionDtoFrom(regionEntityToRegionEntityDto(source.getRegionFrom(), new RegionDTO()));
        dest.setDistanceInKm(source.getDistanceKm());
        dest.setInvocationCount(Long.valueOf(source.getInvocationCount()));
        return dest;
    }

    private RegionDTO regionEntityToRegionEntityDto(RegionEntity source, RegionDTO dest){
        dest.setRegionName(source.getRegionName());
        dest.setCountryName(source.getCountry().getName());
        dest.setLongitude(source.getLongitude());
        dest.setLatitude(source.getLatitude());
        return dest;
    }

}
