package com.banquito.core.aplicacion.cuentas.modelo;

public enum EstadoComision {
    VIGENTE("VIGENTE"),
    INACTIVO("INACTIVO");

    private final String valor;

    EstadoComision(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 