package com.banquito.core.aplicacion.transacciones.excepcion;

public class TransaccionNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TransaccionNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1001;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 