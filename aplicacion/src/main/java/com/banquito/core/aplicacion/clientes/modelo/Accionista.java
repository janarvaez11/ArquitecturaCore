package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="Accionistas")
public class Accionista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAccionista", nullable = false)
    private Integer idAccionista;

    @ManyToOne
    @JoinColumn(name = "IdEmpresa", referencedColumnName = "IdEmpresa")
    private Empresa empresa;

    @Column(name = "Participacion", precision = 5, scale = 2, nullable = false)
    private BigDecimal participacion;

    @Column(name = "TipoEntidad",  length = 50, nullable = false)
    private String tipoEntidad;

    public Accionista() {}

    public Accionista(Integer IdAccionista){
        this.idAccionista = IdAccionista;
    }

    public Integer getIdAccionista() {
        return idAccionista;
    }

    public void setIdAccionista(Integer idAccionista) {
        this.idAccionista = idAccionista;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getParticipacion() {
        return participacion;
    }

    public void setParticipacion(BigDecimal participacion) {
        this.participacion = participacion;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
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
        if (idAccionista == null) {
            if (other.idAccionista != null)
                return false;
        } else if (!idAccionista.equals(other.idAccionista))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAccionista == null) ? 0 : idAccionista.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Accionista{" +
                "IdAccionista=" + idAccionista +
                ", IdEmpresa=" + empresa +
                ", Participacion=" + participacion +
                ", TipoEntidad='" + tipoEntidad + '\'' +
                '}';
    }
}
