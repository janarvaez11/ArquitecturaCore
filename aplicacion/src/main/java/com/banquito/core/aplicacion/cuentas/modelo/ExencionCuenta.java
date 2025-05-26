package com.banquito.core.aplicacion.cuentas.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ExencionesCuentas")


public class ExencionCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdExencion", nullable = false)
    private Integer IdExencion;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String Nombre;

    @Column(name = "Descripcion", length = 100, nullable = false)
    private String Descripcion;

    //Relación con la tabla Comisiones
    @ManyToOne
    @JoinColumn(name = "IdComision", referencedColumnName = "IdComisionCargo", nullable = false)
    private ComisionCargo comision;

    //Constructores
    public ExencionCuenta() {
    }
    public ExencionCuenta(Integer idExencion) {
        IdExencion = idExencion;
    }

    //Getters y Setters
    public Integer getIdExencion() {
        return IdExencion;
    }
    public void setIdExencion(Integer idExencion) {
        IdExencion = idExencion;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
    public ComisionCargo getComision() {
        return comision;
    }
    public void setComision(ComisionCargo comision) {
        this.comision = comision;
    }

    //HashCode y Equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdExencion == null) ? 0 : IdExencion.hashCode());
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
        ExencionCuenta other = (ExencionCuenta) obj;
        if (IdExencion == null) {
            if (other.IdExencion != null)
                return false;
        } else if (!IdExencion.equals(other.IdExencion))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "ExencionCuenta [IdExencion=" + IdExencion + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion
                + ", comision=" + comision + "]";
    }

     //ToString



}