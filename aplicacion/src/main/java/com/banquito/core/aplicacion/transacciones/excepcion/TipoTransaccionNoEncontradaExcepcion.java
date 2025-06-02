package com.banquito.core.aplicacion.transacciones.excepcion;

public class TipoTransaccionNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TipoTransaccionNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1002;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 