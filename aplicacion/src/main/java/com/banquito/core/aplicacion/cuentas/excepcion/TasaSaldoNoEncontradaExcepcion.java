package com.banquito.core.aplicacion.cuentas.excepcion;

public class TasaSaldoNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;
    private final String entidad;

    public TasaSaldoNoEncontradaExcepcion(String entidad, String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = entidad;
    }

    public TasaSaldoNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = "TasaSaldo";
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", Entidad: " + this.entidad + ", Mensaje: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getEntidad() {
        return entidad;
    }
} 