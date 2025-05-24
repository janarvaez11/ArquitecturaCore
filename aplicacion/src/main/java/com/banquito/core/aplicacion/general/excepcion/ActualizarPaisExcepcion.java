package com.banquito.core.aplicacion.general.excepcion;

public class ActualizarPaisExcepcion extends RuntimeException {

    private final Integer codigoError;
    private static final Integer CODIGO_ERROR_DEFAULT = 1003;

    public ActualizarPaisExcepcion(String nombreEntidad, String mensaje) {
        super(mensaje);
        this.codigoError = CODIGO_ERROR_DEFAULT;
    }

    public ActualizarPaisExcepcion(String nombreEntidad, String mensaje, Integer codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    @Override
    public String getMessage() {
        return "Código de error: " + this.codigoError + ", Entidad: Pais" + ", Mensaje: " + super.getMessage();
    }

    public Integer getCodigoError() {
        return codigoError;
    }
}
