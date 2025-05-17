package com.mercadolibre.app_geo.exception.messages;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    COUNTRY_NOT_FOUND("COUNTRY_NOT_FOUND", "No se encontró un país con el código ISO: {0}"),
    REGION_NOT_FOUND("REGION_NOT_FOUND", "No se encontró la region con el nombre: {0}"),
    INVALID_REQUEST("INVALID_REQUEST", "La solicitud es inválida: {0}"),
    INVALID_IP("INVALID_IP", "La IP {0} no es publica o no tiene un formato valido"),
    EXCHANGE_RATE_ERROR_API("EXCHANGE_RATE_ERROR_API", "Error al obtener tasas de cambio de fuentes externas"),
    IP_ERROR_API("IP_ERROR_API", "Error al obtener informacion de ubicacion de Api externa")
    ;


    private final String code;
    private final String message;

    ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
