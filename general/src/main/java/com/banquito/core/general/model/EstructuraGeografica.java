package com.banquito.core.general.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EstructuraGeografica")
public class EstructuraGeografica {

    @EmbeddedId
    private IdEstructuraGeografica id;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String Nombre;

    //Relaciones
    @ManyToOne
    @JoinColumn(name= "IdPais", referencedColumnName = "IdPais", insertable = false, updatable = false)
    private Pais pais;



    // Constructor
    public EstructuraGeografica() {
    }

    public EstructuraGeografica(IdEstructuraGeografica id) {
        this.id = id;
    }

    public IdEstructuraGeografica getId() {
        return id;
    }

    public void setId(IdEstructuraGeografica id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    

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
        EstructuraGeografica other = (EstructuraGeografica) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EstructuraGeografica [id=" + id + ", Nombre=" + Nombre + "]";
    }



}
