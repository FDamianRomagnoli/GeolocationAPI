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
@Table(name = "EXCHANGE_RATE_ENTITY")
@Data
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nm_exchange_rate_id")
    private Integer id;

    @Column(name = "va_conversion_date", nullable = false)
    private String conversionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nm_currency_id_from", nullable = false,
            foreignKey = @ForeignKey(name = "exchange_rate_currency_entity_nm_currency_id_fk"))
    private CurrencyEntity currencyFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nm_currency_id_to", nullable = false,
            foreignKey = @ForeignKey(name = "exchange_rate_currency_entity_nm_currency_id_fk2"))
    private CurrencyEntity currencyTo;

    @Column(name = "nm_conversion_rate", nullable = false)
    private Double conversionRate;

}
