package com.banquito.core.aplicacion.transacciones.excepcion;

public class MontoInvalidoExcepcion extends RuntimeException {

    private final Integer errorCode;

    public MontoInvalidoExcepcion(String message) {
        super(message);
        this.errorCode = 1004;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 