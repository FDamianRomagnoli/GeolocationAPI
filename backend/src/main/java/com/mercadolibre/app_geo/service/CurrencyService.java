package com.mercadolibre.app_geo.service;

import com.mercadolibre.app_geo.dao.CurrencyDao;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
public class CurrencyService {

    @Autowired
    CurrencyDao currencyDao;

    @Transactional
    public CurrencyEntity findOrCreate(String currencyCode) {

        Optional<CurrencyEntity> currencyEntityOptional = currencyDao.findByCurrencyCode(currencyCode);

        if(currencyEntityOptional.isEmpty()){
            CurrencyEntity currencyEntity = new CurrencyEntity();
            currencyEntity.setCurrencyCode(currencyCode);
            currencyEntity.setCreateDate(ZonedDateTime.now());

            return currencyDao.save(currencyEntity);
        }
        else{
            return currencyEntityOptional.get();
        }

    }

}
