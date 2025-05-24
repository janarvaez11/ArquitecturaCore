package com.banquito.core.aplicacion.cuentas.excepcion;

public class TipoCuentaNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;

    public TipoCuentaNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", message" + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
