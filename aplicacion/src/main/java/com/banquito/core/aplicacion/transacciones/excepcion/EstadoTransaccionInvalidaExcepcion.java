package com.banquito.core.aplicacion.transacciones.excepcion;

public class EstadoTransaccionInvalidaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public EstadoTransaccionInvalidaExcepcion(String message) {
        super(message);
        this.errorCode = 1007;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 