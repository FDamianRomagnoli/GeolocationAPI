package com.mercadolibre.app_geo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TimeZoneDbResponse {
    private String status;
    private String message;
    private List<Zone> zones;

    @Data
    public static class Zone {
        private String countryCode;
        private String countryName;
        private String zoneName;
        private int gmtOffset;
        private long timestamp;
    }

}
