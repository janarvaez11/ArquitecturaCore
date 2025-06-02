package com.banquito.core.aplicacion.transacciones.excepcion;

public class CuentaInvalidaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public CuentaInvalidaExcepcion(String message) {
        super(message);
        this.errorCode = 1005;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 