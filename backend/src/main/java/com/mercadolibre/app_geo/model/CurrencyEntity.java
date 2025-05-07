package com.mercadolibre.app_geo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Table(name = "CURRENCY_ENTITY")
@Data
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NM_CURRENCY_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "VA_CODE")
    private String currencyCode;

    @Column(name = "TS_CREATE")
    protected ZonedDateTime createDate;
}
