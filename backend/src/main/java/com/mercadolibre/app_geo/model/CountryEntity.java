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
@Table(name = "COUNTRY_ENTITY")
@Data
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NM_COUNTRY_ID", unique = true, nullable = false)
    private Long countryId;

    @Column(name = "VA_ISO_CODE", nullable = false, length = 24)
    private String isoCode;

    @Column(name = "VA_NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "TS_CREATE", nullable = false)
    private ZonedDateTime createDate;

}
