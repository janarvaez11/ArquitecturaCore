package com.banquito.core.aplicacion.cuentas.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ServiciosAsociados")
public class ServicioAsociado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa IDENTITY para campos SERIAL en PostgreSQL
    @Column(name = "IdServicio", nullable = false)
    private Integer IdServicio;

    @Column(name = "Nombre", length = 30, nullable = false)
    private String Nombre;

    @Column(name = "Descripcion", length = 150, nullable = false)
    private String Descripcion;

    @Column(name = "Estado", length = 30, nullable = false)
    private String Estado;

    // Constructores
    public ServicioAsociado() {
    }

    public ServicioAsociado(Integer idServicio) {
        IdServicio = idServicio;
    }

    // Getters y Setters
    public Integer getIdServicio() {
        return IdServicio;
    }

    public void setIdServicio(Integer idServicio) {
        IdServicio = idServicio;
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

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    // Métodos hashCode y equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdServicio == null) ? 0 : IdServicio.hashCode());
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
        ServicioAsociado other = (ServicioAsociado) obj;
        if (IdServicio == null) {
            if (other.IdServicio != null)
                return false;
        } else if (!IdServicio.equals(other.IdServicio))
            return false;
        return true;
    }

    // Método toString
    @Override
    public String toString() {
        return "ServicioAsociado [IdServicio=" + IdServicio + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion
                + ", Estado=" + Estado + "]";
    }

}
