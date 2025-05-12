package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dao.CountryDao;
import com.mercadolibre.app_geo.model.CountryEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
public class CountryService {


    private final CountryDao countryDao;

    @Autowired
    public CountryService(
            CountryDao countryDao
    ){
        this.countryDao = countryDao;
    }

    @Transactional
    public CountryEntity findOrCreate(String countryName, String countryCode) {
        Optional<CountryEntity> countryEntityOptional = countryDao.findByNameAndIsoCode(countryName, countryCode);

        if(countryEntityOptional.isEmpty()){
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCreateDate(ZonedDateTime.now());
            countryEntity.setName(countryName);
            countryEntity.setIsoCode(countryCode);

            return countryDao.save(countryEntity);
        }
        else{
            return countryEntityOptional.get();
        }


    }
}
