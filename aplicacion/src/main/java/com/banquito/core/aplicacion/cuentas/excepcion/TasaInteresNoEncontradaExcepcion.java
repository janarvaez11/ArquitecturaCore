package com.banquito.core.aplicacion.cuentas.excepcion;

public class TasaInteresNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;
    private final String entidad;

    public TasaInteresNoEncontradaExcepcion(String entidad, String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = entidad;
    }

    public TasaInteresNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = "TasaInteres";
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