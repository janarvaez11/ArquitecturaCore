package com.banquito.core.aplicacion.cuentas.modelo;

public enum BaseCalculoComision {
    EXENTO("EXENTO"),
    PORCENTAJE("PORCENTAJE"),
    MONTO_FIJO("MONTO_FIJO");

    private final String valor;

    BaseCalculoComision(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 