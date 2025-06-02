package com.banquito.core.aplicacion.transacciones.excepcion;

public class RetiroNoEncontradoExcepcion extends RuntimeException {

    private final Integer errorCode;

    public RetiroNoEncontradoExcepcion(String message) {
        super(message);
        this.errorCode = 1009;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 