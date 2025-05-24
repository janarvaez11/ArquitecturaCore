package com.banquito.core.general.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Paises")

public class Pais {

    @Id
    @Column(name = "IdPais", length = 2, nullable = false)
    private String id;

    @Column(name = "CodigoTelefono", length = 4, nullable = false)
    private String CodigoTelefono;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String Nombre;

    // Relacion con EstructuraGeografica
    @OneToMany(mappedBy = "pais")
    private List<EstructuraGeografica> estructurasGeograficas;

    // Construcciones
    public Pais() {
    }

    public Pais(String id) {
        this.id = id;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoTelefono() {
        return CodigoTelefono;
    }

    public void setCodigoTelefono(String codigoTelefono) {
        CodigoTelefono = codigoTelefono;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public List<EstructuraGeografica> getEstructurasGeograficas() {
        return estructurasGeograficas;
    }

    public void setEstructurasGeograficas(List<EstructuraGeografica> estructurasGeograficas) {
        this.estructurasGeograficas = estructurasGeograficas;
    }

    // HashCode y Equals
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
        Pais other = (Pais) obj;
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
        return "Pais [id=" + id + ", CodigoTelefono=" + CodigoTelefono + ", Nombre=" + Nombre + "]";
    }

}
