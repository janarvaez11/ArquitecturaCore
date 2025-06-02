package com.banquito.core.aplicacion.transacciones.excepcion;

public class TransferenciaNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TransferenciaNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1011;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 