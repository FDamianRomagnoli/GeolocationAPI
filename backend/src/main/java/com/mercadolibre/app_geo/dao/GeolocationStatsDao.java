package com.mercadolibre.app_geo.dao;

import com.mercadolibre.app_geo.model.GeolocationStatsEntity;
import com.mercadolibre.app_geo.model.RegionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GeolocationStatsDao extends CrudRepository<GeolocationStatsEntity, Long> {

    Optional<GeolocationStatsEntity> findByRegionFromAndRegionTo(RegionEntity regionFrom, RegionEntity regionTo);

    @Query(value = " SELECT  " +
        " SUM(gse.nm_distance_km * gse.nm_invocation) / " +
        " SUM(gse.nm_invocation) " +
        " FROM geolocation_stats_entity gse ", nativeQuery = true)
    Double calculateAbs();

    @Query(value = "SELECT * FROM geolocation_stats_entity ORDER BY nm_distance_km ASC LIMIT 1", nativeQuery = true)
    GeolocationStatsEntity findByMinDistance();

    @Query(value = "SELECT * FROM geolocation_stats_entity ORDER BY nm_distance_km DESC LIMIT 1", nativeQuery = true)
    GeolocationStatsEntity findByMaxDistance();
}
