package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dao.RegionDao;
import com.mercadolibre.app_geo.exception.BusinessException;
import com.mercadolibre.app_geo.exception.messages.ErrorMessage;
import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.model.RegionEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
public class RegionService{

    @Autowired
    RegionDao regionDao;

    @Transactional
    public RegionEntity findOrCreate(String regionName, Double latitude, Double longitude, CountryEntity country) {
        Optional<RegionEntity> regionEntityOptional = regionDao.findByRegionNameAndCountry(regionName, country);

        if(regionEntityOptional.isEmpty()){
            RegionEntity regionEntity = new RegionEntity();
            regionEntity.setRegionName(regionName);
            regionEntity.setLatitude(latitude);
            regionEntity.setLongitude(longitude);
            regionEntity.setCountry(country);
            regionEntity.setCreateDate(ZonedDateTime.now());

            return regionDao.save(regionEntity);
        }
        else{
            return regionEntityOptional.get();
        }

    }

    @Transactional
    public RegionEntity findByRegionName(String regionName){

        Optional<RegionEntity> regionDefaultOpt = regionDao.findByRegionName(regionName);

        if(regionDefaultOpt.isPresent()) {
            return regionDefaultOpt.get();
        }
        else{
            throw new BusinessException(ErrorMessage.REGION_NOT_FOUND.getCode(), ErrorMessage.REGION_NOT_FOUND.getMessage(), regionName);
        }

    }
}
