package com.mercadolibre.app_geo.dto;

import lombok.Data;

import java.util.List;

@Data
public class IpApiResponse {
    private String ip;
    private String type;
    private String continent_code;
    private String continent_name;
    private String country_code;
    private String country_name;
    private String region_code;
    private String region_name;
    private String city;
    private String zip;
    private Double latitude;
    private Double longitude;

    private Location location;
    private TimeZone time_zone;
    private Currency currency;
    private Connection connection;
    private Security security;

    @Data
    public static class Location {
        private int geoname_id;
        private String capital;
        private List<Language> languages;
        private String country_flag;
        private String country_flag_emoji;
        private String country_flag_emoji_unicode;
        private String calling_code;
        private boolean is_eu;
    }

    @Data
    public static class Language {
        private String code;
        private String name;
        private String native_;
    }

    @Data
    public static class TimeZone {
        private String id;
        private String current_time;
        private int gmt_offset;
        private String code;
        private boolean is_daylight_saving;
    }

    @Data
    public static class Currency {
        private String code;
        private String name;
        private String plural;
        private String symbol;
        private String symbol_native;
    }

    @Data
    public static class Connection {
        private int asn;
        private String isp;
        private String sld;
        private String tld;
        private String carrier;
        private boolean home;
        private String organization_type;
        private String isic_code;
        private String naics_code;
    }

    @Data
    public static class Security {
        private Boolean is_proxy;
        private String proxy_type;
        private Boolean is_crawler;
        private String crawler_name;
        private String crawler_type;
        private Boolean is_tor;
        private String threat_level;
        private String threat_types;
        private String proxy_last_detected;
        private String proxy_level;
        private String vpn_service;
        private String anonymizer_status;
        private Boolean hosting_facility;
    }
}