package com.banquito.core.aplicacion.transacciones.excepcion;

public class RetiroInvalidoExcepcion extends RuntimeException {

    private final Integer errorCode;

    public RetiroInvalidoExcepcion(String message) {
        super(message);
        this.errorCode = 1008;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 