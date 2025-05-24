package com.banquito.core.aplicacion.cuentas.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="TasaPlazos")

public class TasaPlazo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPlazo", nullable = false)
    private Integer IdPlazo;

    @Column(name = "PlazoMinimo", nullable = false)
    private Integer PlazoMinimo;

    @Column(name = "PlazoMaximo", nullable = false)
    private Integer PlazoMaximo;

    @Column(name = "Tasa", precision = 5, scale = 2)
    private BigDecimal Tasa;

    public TasaPlazo() {
    }
    public TasaPlazo(Integer idPlazo) {
        IdPlazo = idPlazo;
    }
    public Integer getIdPlazo() {
        return IdPlazo;
    }
    public void setIdPlazo(Integer idPlazo) {
        IdPlazo = idPlazo;
    }
    public Integer getPlazoMinimo() {
        return PlazoMinimo;
    }
    public void setPlazoMinimo(Integer plazoMinimo) {
        PlazoMinimo = plazoMinimo;
    }
    public Integer getPlazoMaximo() {
        return PlazoMaximo;
    }
    public void setPlazoMaximo(Integer plazoMaximo) {
        PlazoMaximo = plazoMaximo;
    }
    public BigDecimal getTasa() {
        return Tasa;
    }
    public void setTasa(BigDecimal tasa) {
        Tasa = tasa;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdPlazo == null) ? 0 : IdPlazo.hashCode());
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
        TasaPlazo other = (TasaPlazo) obj;
        if (IdPlazo == null) {
            if (other.IdPlazo != null)
                return false;
        } else if (!IdPlazo.equals(other.IdPlazo))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "TasaPlazo [IdPlazo=" + IdPlazo + ", PlazoMinimo=" + PlazoMinimo + ", PlazoMaximo=" + PlazoMaximo
                + ", Tasa=" + Tasa + "]";
    }

}
