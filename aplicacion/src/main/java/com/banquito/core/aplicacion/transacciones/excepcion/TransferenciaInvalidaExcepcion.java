package com.banquito.core.aplicacion.transacciones.excepcion;

public class TransferenciaInvalidaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TransferenciaInvalidaExcepcion(String message) {
        super(message);
        this.errorCode = 1010;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 