package com.banquito.core.aplicacion.cuentas.modelo;

public enum EstadoTasaInteres {
    VIGENTE("VIGENTE"),
    INACTIVO("INACTIVO");

    private final String valor;

    EstadoTasaInteres(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 