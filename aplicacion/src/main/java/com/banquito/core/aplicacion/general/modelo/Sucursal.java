package com.banquito.core.aplicacion.general.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Sucursales")
public class Sucursal {

    @Id
    @Column(name = "CodigoSucursal", length = 3, nullable = false)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "IdLocacion", referencedColumnName = "IdLocacion")
    private LocacionGeografica locacion;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "Direccion", length = 200)
    private String direccion;

    @Column(name = "Telefono", length = 15)
    private String telefono;

    @Column(name = "Estado", length = 3, nullable = false)
    private String estado;

    public Sucursal() {
    }

    public Sucursal(String codigo, LocacionGeografica locacion) {
        this.codigo = codigo;
        this.locacion = locacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocacionGeografica getLocacion() {
        return locacion;
    }

    public void setLocacion(LocacionGeografica locacion) {
        this.locacion = locacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((locacion == null) ? 0 : locacion.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sucursal other = (Sucursal) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (locacion == null) {
            if (other.locacion != null)
                return false;
        } else if (!locacion.equals(other.locacion))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sucursal [codigo=" + codigo + ", locacion=" + locacion + ", nombre=" + nombre + ", direccion="
                + direccion + ", telefono=" + telefono + ", estado=" + estado + "]";
    }
}