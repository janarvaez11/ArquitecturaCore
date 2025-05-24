package com.banquito.core.general.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class IdEstructuraGeografica implements Serializable {

    @Column(name = "IdPais", length = 2, nullable = false)
    private String IdPais;

    @Column(name = "CodigoNivel", length = 1, nullable = false)
    private Integer CodigoNivel;

    public IdEstructuraGeografica() {
    }

    public IdEstructuraGeografica(String idPais, Integer codigoNivel) {
        IdPais = idPais;
        CodigoNivel = codigoNivel;
    }

    public String getIdPais() {
        return IdPais;
    }

    public void setIdPais(String idPais) {
        IdPais = idPais;
    }

    public Integer getCodigoNivel() {
        return CodigoNivel;
    }

    public void setCodigoNivel(Integer codigoNivel) {
        CodigoNivel = codigoNivel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdPais == null) ? 0 : IdPais.hashCode());
        result = prime * result + ((CodigoNivel == null) ? 0 : CodigoNivel.hashCode());
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
        IdEstructuraGeografica other = (IdEstructuraGeografica) obj;
        if (IdPais == null) {
            if (other.IdPais != null)
                return false;
        } else if (!IdPais.equals(other.IdPais))
            return false;
        if (CodigoNivel == null) {
            if (other.CodigoNivel != null)
                return false;
        } else if (!CodigoNivel.equals(other.CodigoNivel))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "IdEstructuraGeografica [IdPais=" + IdPais + ", CodigoNivel=" + CodigoNivel + "]";
    }

}
