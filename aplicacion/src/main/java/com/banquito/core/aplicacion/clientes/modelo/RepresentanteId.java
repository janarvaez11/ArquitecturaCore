package com.banquito.core.aplicacion.clientes.modelo;

import java.io.Serializable;
import java.util.Objects;

public class RepresentanteId implements Serializable {
    private Integer idEmpresa;
    private Integer idCliente;
    private String rol;

    public RepresentanteId() {}

    public RepresentanteId(Integer idEmpresa, Integer idCliente, String rol) {
        this.idEmpresa = idEmpresa;
        this.idCliente = idCliente;
        this.rol = rol;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepresentanteId that = (RepresentanteId) o;
        return Objects.equals(idEmpresa, that.idEmpresa) &&
                Objects.equals(idCliente, that.idCliente) &&
                Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpresa, idCliente, rol);
    }

    @Override
    public String toString() {
        return "RepresentanteId{" +
                "idEmpresa=" + idEmpresa +
                ", idCliente=" + idCliente +
                ", rol='" + rol + '\'' +
                '}';
    }
}
