package com.banquito.core.general.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "Monedas")

public class Moneda {

    @Id
    @Column(name = "id", length = 3, nullable = false)
    private String id;

    @Column(name = "IdPais", length = 2, nullable = false)
    private String IdPais;

    @Column(name = "IdEntidadBancaria", nullable = false)
    private Integer IdEntidadBancaria;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String Nombre;

    @Column(name = "Simbolo", length = 3, nullable = false)
    private String Simbolo;

    @Column(name = "Codigo", length = 3, nullable = false, unique = true)
    private String Codigo;

    @Version
    private Long Version;

    @ManyToOne
    @JoinColumn(name = "IdPais", referencedColumnName = "IdPais")
    private Pais pais;

    // Constructores
    public Moneda() {
    }

    public Moneda(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPais() {
        return IdPais;
    }

    public void setIdPais(String idPais) {
        IdPais = idPais;
    }

    public Integer getIdEntidadBancaria() {
        return IdEntidadBancaria;
    }

    public void setIdEntidadBancaria(Integer idEntidadBancaria) {
        IdEntidadBancaria = idEntidadBancaria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getSimbolo() {
        return Simbolo;
    }

    public void setSimbolo(String simbolo) {
        Simbolo = simbolo;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public Long getVersion() {
        return Version;
    }

    public void setVersion(Long version) {
        this.Version = version;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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
        Moneda other = (Moneda) obj;
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
        return "Moneda [id=" + id + ", IdPais=" + IdPais + ", IdEntidadBancaria=" + IdEntidadBancaria + ", Nombre="
                + Nombre + ", Simbolo=" + Simbolo + ", Codigo=" + Codigo + ", Version=" + Version + ", pais=" + pais
                + "]";
    }

}
