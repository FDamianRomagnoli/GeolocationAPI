package com.mercadolibre.app_geo.service.impl;

import com.mercadolibre.app_geo.dao.CurrencyDao;
import com.mercadolibre.app_geo.model.CurrencyEntity;
import com.mercadolibre.app_geo.service.interfaces.CurrencyService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    CurrencyDao currencyDao;

    @Override
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
