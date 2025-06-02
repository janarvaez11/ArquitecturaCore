package com.banquito.core.aplicacion.transacciones.excepcion;

public class EstadoTransaccionNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public EstadoTransaccionNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1003;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 