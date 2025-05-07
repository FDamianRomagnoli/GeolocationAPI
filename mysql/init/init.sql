create table country_entity
(
    nm_country_id bigint auto_increment
        primary key,
    va_iso_code   varchar(24)  not null,
    va_name       varchar(255) not null,
    ts_create     TIMESTAMP NOT NULL
);

create table region_entity
(
    nm_region_id   bigint auto_increment
        primary key,
    va_region_name varchar(255) not null,
    nm_country_id  bigint          not null,
    ts_create      timestamp    not null,
    nm_latitude    double       not null,
    nm_longitude   double       not null,
    constraint region_entity_country_entity_nm_country_id_fk
        foreign key (nm_country_id) references country_entity (nm_country_id)
            on update cascade on delete cascade
);

create table currency_entity
(
    nm_currency_id bigint auto_increment PRIMARY KEY,
    ts_create     timestamp not null,
    va_code        varchar(15) not null
);

create table exchange_rate_entity
(
    nm_exchange_rate_id bigint auto_increment  
        primary key,
    va_conversion_date  varchar(255) not null,
    nm_currency_id_from    bigint       not null,
    nm_currency_id_to      bigint       not null,
    nm_conversion_rate  double    not null,
    constraint exchange_rate_currency_entity_nm_currency_id_fk
        foreign key (nm_currency_id_from) references currency_entity (nm_currency_id),
    constraint exchange_rate_currency_entity_nm_currency_id_fk2
        foreign key (nm_currency_id_to) references currency_entity (nm_currency_id)
);

create table geolocation_stats_entity
(
    nm_geolocation_stats_id bigint auto_increment
        primary key,
    ts_create               timestamp not null,
    nm_region_id_from       bigint       not null,
    nm_region_id_to         bigint       not null,
    nm_distance_km          double    not null,
    nm_invocation           bigint       null,
    constraint geolocation_stats_entity_region_entity_nm_region_id_fk
        foreign key (nm_region_id_from) references region_entity (nm_region_id),
    constraint geolocation_stats_entity_region_entity_nm_region_id_fk2
        foreign key (nm_region_id_to) references region_entity (nm_region_id)
);


insert into country_entity (va_iso_code, ts_create, va_name) VALUES
 ('AR', now(), 'Argentina');

INSERT INTO region_entity (va_region_name, nm_country_id, ts_create, nm_latitude, nm_longitude)
SELECT 'Buenos Aires', nm_country_id, NOW(), -34.82720184326172, -58.46200180053711
FROM country_entity
WHERE va_name = 'Argentina';

INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'USD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'FJD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MXN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'STD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LVL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SCR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CDF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BBD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GTQ');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CLP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'HNL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'UGX');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ZAR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TND');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SLE');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CUC');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BSD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SLL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SDG');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'IQD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CUP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GMD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TWD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'RSD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'DOP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KMF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MYR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'FKP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XOF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GEL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BTC');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'UYU');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MAD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CVE');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TOP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AZN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'OMR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PGK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KES');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SEK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CNH');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BTN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'UAH');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GNF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ERN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MZN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SVC');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ARS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'QAR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'IRR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CNY');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'THB');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'UZS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XPF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MRU');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BDT');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LYD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BMD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KWD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PHP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'RUB');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PYG');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ISK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'JMD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'COP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MKD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'DZD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PAB');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GGP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SGD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ETB');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'JEP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KGS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SOS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'VUV');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LAK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BND');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ZMK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XAF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LRD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XAG');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CHF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'HRK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ALL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'DJF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'VES');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ZMW');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TZS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'VND');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XAU');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AUD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ILS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GHS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GYD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KPW');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BOB');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KHR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MDL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'IDR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KYD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AMD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BWP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SHP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TRY');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LBP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TJS');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'JOD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AED');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'HKD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'RWF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'EUR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LSL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'DKK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CAD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BGN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MMK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MUR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'NOK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SYP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'IMP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ZWL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GIP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'RON');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LKR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'NGN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CRC');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CZK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PKR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XCD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'ANG');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'HTG');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BHD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KZT');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SRD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SZL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'LTL');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SAR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TTD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'YER');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MVR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AFN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'INR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AWG');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'KRW');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'NPR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'JPY');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MNT');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'AOA');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PLN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'GBP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'SBD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BYN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'HUF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BYR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BIF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MWK');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MGA');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'XDR');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BZD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BAM');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'EGP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'MOP');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'NAD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'NIO');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'PEN');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'NZD');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'WST');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'TMT');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'CLF');
INSERT INTO geo_db.currency_entity ( ts_create, va_code) VALUES( now(), 'BRL');
