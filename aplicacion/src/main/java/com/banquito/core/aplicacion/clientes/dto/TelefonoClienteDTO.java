package com.banquito.core.aplicacion.clientes.dto;

public class TelefonoClienteDTO {

    private Integer idTelefonoCliente;
    private Integer idCliente;
    private String tipoTelefono;
    private String numeroTelefono;
    private String estado;

    public TelefonoClienteDTO() {}

    public Integer getIdTelefonoCliente() {
        return idTelefonoCliente;
    }

    public void setIdTelefonoCliente(Integer idTelefonoCliente) {
        this.idTelefonoCliente = idTelefonoCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

