package com.banquito.core.aplicacion.cuentas.excepcion;

public class ComisionCargoNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;
    private final String entidad;

    public ComisionCargoNoEncontradaExcepcion(String entidad, String mensaje) {
        super(String.format("La entidad %s no fue encontrada. %s", entidad, mensaje));
        this.errorCode = 1;
        this.entidad = entidad;
    }

    public ComisionCargoNoEncontradaExcepcion(String message) {
        super(message);
        this.errorCode = 1;
        this.entidad = "ComisionCargo";
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