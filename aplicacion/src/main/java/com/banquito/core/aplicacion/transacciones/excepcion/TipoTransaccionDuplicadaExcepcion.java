package com.banquito.core.aplicacion.transacciones.excepcion;

public class TipoTransaccionDuplicadaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TipoTransaccionDuplicadaExcepcion(String message) {
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