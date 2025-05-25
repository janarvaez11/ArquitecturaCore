package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="Accionistas")
public class Accionista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAccionista", nullable = false)
    private Integer IdAccionista;

    @ManyToOne
    @JoinColumn(name = "IdEmpresa", referencedColumnName = "IdEmpresa")
    private Empresa IdEmpresa;

    @Column(name = "Participacion", precision = 5, scale = 2, nullable = false)
    private BigDecimal Participacion;

    @Column(name = "TipoEntidad",  length = 50, nullable = false)
    private String TipoEntidad;

    public Accionista() {}

    public Accionista(Integer IdAccionista){
        this.IdAccionista = IdAccionista;
    }

    public Integer getIdAccionista() {
        return IdAccionista;
    }

    public void setIdAccionista(Integer idAccionista) {
        IdAccionista = idAccionista;
    }

    public Empresa getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public BigDecimal getParticipacion() {
        return Participacion;
    }

    public void setParticipacion(BigDecimal participacion) {
        Participacion = participacion;
    }

    public String getTipoEntidad() {
        return TipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        TipoEntidad = tipoEntidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Accionista other = (Accionista) obj;
        if (IdAccionista == null) {
            if (other.IdAccionista != null)
                return false;
        } else if (!IdAccionista.equals(other.IdAccionista))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdAccionista == null) ? 0 : IdAccionista.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Accionista{" +
                "IdAccionista=" + IdAccionista +
                ", IdEmpresa=" + IdEmpresa +
                ", Participacion=" + Participacion +
                ", TipoEntidad='" + TipoEntidad + '\'' +
                '}';
    }
}
