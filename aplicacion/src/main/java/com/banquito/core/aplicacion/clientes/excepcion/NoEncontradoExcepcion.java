package com.banquito.core.aplicacion.clientes.excepcion;


public class NoEncontradoExcepcion extends RuntimeException {

    private final String dato;
    private final String entidad;

    public NoEncontradoExcepcion(String dato, String entidad) {
        this.dato = dato;
        this.entidad = entidad;
    }

    @Override
    public String getMessage() {
        return "No se encontró ninguna coincidencia para: " + entidad + ", con el dato: " + dato;
    }
}
