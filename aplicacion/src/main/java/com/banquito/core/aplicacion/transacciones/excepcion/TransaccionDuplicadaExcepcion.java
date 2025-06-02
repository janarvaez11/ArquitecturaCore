package com.banquito.core.aplicacion.transacciones.excepcion;

public class TransaccionDuplicadaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TransaccionDuplicadaExcepcion(String message) {
        super(message);
        this.errorCode = 1006;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
} 