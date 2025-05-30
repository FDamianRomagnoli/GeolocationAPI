package com.mercadolibre.app_geo.service.impl;

import com.mercadolibre.app_geo.dao.CountryDao;
import com.mercadolibre.app_geo.model.CountryEntity;
import com.mercadolibre.app_geo.service.interfaces.CountryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryDao countryDao;

    @Override
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
