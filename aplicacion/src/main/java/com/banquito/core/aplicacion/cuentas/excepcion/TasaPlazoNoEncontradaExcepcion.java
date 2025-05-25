package com.banquito.core.aplicacion.cuentas.excepcion;

public class TasaPlazoNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;
    private final String entidad;

    public TasaPlazoNoEncontradaExcepcion(String entidad, String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = entidad;
    }

    public TasaPlazoNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = "TasaPlazo";
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