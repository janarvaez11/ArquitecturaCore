package com.banquito.core.aplicacion.clientes.modelo;

import java.io.Serializable;
import java.util.Objects;

public class RepresentanteId implements Serializable {
    private Integer IdEmpresa;
    private Integer IdCliente;
    private String Rol;

    public RepresentanteId() {}

    public RepresentanteId(Integer idEmpresa, Integer idCliente, String rol) {
        this.IdEmpresa = idEmpresa;
        this.IdCliente = idCliente;
        this.Rol = rol;
    }

    public Integer getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer idCliente) {
        IdCliente = idCliente;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepresentanteId that = (RepresentanteId) o;
        return Objects.equals(IdEmpresa, that.IdEmpresa) &&
                Objects.equals(IdCliente, that.IdCliente) &&
                Objects.equals(Rol, that.Rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdEmpresa, IdCliente, Rol);
    }

    @Override
    public String toString() {
        return "RepresentanteId{" +
                "IdEmpresa=" + IdEmpresa +
                ", IdCliente=" + IdCliente +
                ", Rol='" + Rol + '\'' +
                '}';
    }
}
