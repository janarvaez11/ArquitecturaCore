package com.banquito.core.general.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EntidadesBancarias")

public class EntidadBancaria {

    // Mapeo de la tabla EntidadesBancarias
    @Id
    @Column(name = "IdEntidadBancaria", nullable = false)
    private Integer id;

    @Column(name = "CodigoLocal", length = 6, nullable = false)
    private String CodigoLocal;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String Nombre;

    @Column(name = "CodigoInternacional", length = 20, nullable = false)
    private String CodigoInternacional;

    // Constructores

    public EntidadBancaria() {
    }

    public EntidadBancaria(Integer id) {
        this.id = id;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoLocal() {
        return CodigoLocal;
    }

    public void setCodigoLocal(String codigoLocal) {
        CodigoLocal = codigoLocal;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCodigoInternacional() {
        return CodigoInternacional;
    }

    public void setCodigoInternacional(String codigoInternacional) {
        CodigoInternacional = codigoInternacional;
    }

    // Metodo Equals y HashCode

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        EntidadBancaria other = (EntidadBancaria) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // Metodo toString

    @Override
    public String toString() {
        return "EntidadesBancarias [id=" + id + ", CodigoLocal=" + CodigoLocal + ", Nombre=" + Nombre
                + ", CodigoInternacional=" + CodigoInternacional + "]";
    }

}
