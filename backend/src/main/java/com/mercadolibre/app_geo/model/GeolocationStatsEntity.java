package com.mercadolibre.app_geo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Table(name = "geolocation_stats_entity")
@Data
public class GeolocationStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_geolocation_stats_id")
    private Integer id;

    @Column(name = "ts_create", nullable = false)
    private ZonedDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "nm_region_id_from", nullable = false)
    private RegionEntity regionFrom;

    @ManyToOne
    @JoinColumn(name = "nm_region_id_to", nullable = false)
    private RegionEntity regionTo;

    @Column(name = "nm_distance_km", nullable = false)
    private Double distanceKm;

    @Column(name = "nm_invocation")
    private Integer invocationCount;

}
