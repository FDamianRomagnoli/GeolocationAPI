package com.mercadolibre.app_geo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Table(name = "REGION_ENTITY")
@Data
public class RegionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NM_REGION_ID")
    private Integer id;

    @Column(name = "VA_REGION_NAME", nullable = false, length = 255)
    private String regionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NM_COUNTRY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "region_entity_country_entity_nm_country_id_fk"))
    private CountryEntity country;

    @Column(name = "TS_CREATE", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "nm_latitude", nullable = false)
    private Double latitude;

    @Column(name = "nm_longitude", nullable = false)
    private Double longitude;

}
