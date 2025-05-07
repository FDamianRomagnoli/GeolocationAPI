package com.mercadolibre.app_geo.exception;

import lombok.Getter;

import java.io.Serializable;
import java.text.MessageFormat;

@Getter
public class BusinessException extends RuntimeException {
    private final String exceptionCode;
    private final Serializable[] data;

    public BusinessException(String exceptionCode, String message, Serializable... data) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.data = data;
    }

    public String getProcessMessage() {
        return (getMessage() != null)
                ? MessageFormat.format(getMessage(), (Object[]) data)
                : null;
    }

    @Override
    public String toString() {
        return MessageFormat.format(super.toString(), (Object[]) data);
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(super.getMessage(), (Object[]) data);
    }
}