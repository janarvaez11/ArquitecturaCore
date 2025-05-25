package com.banquito.core.aplicacion.cuentas.excepcion;

public class EliminarEntidadExcepcion extends RuntimeException {

    private Integer errorCode;
    private String entityName;

    public EliminarEntidadExcepcion(String entityName, String message){
        super(message);
        this.errorCode = 2;
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "errorCode=" + errorCode + ", entityName=" + entityName + ", message=" + super.getMessage();
    }

    

}
