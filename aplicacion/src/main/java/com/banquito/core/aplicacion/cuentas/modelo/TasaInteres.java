package com.banquito.core.aplicacion.cuentas.modelo;

import java.util.Date;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "TasasIntereses")
public class TasaInteres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa IDENTITY para campos SERIAL en PostgreSQL
    @Column(name = "IdTasaInteres", nullable = false)
    private Integer IdTasaInteres;

    @Column(name = "BaseCalculo", length = 10, nullable = false)
    private String BaseCalculo;

    @Column(name = "MetodoCalculo", length = 20, nullable = false)
    private String MetodoCalculo;

    @Column(name = "FrecuenciaCapitalizacion", length = 30, nullable = false)
    private String FrecuenciaCapitalizacion;

    @Column(name = "Estado", length = 15, nullable = false)
    private Instant Estado;

    @Temporal (TemporalType.TIMESTAMP)
    @Column(name = "FechaInicioVigencia", nullable = false)
    private Date FechaInicioVigencia;

    @Temporal (TemporalType.TIMESTAMP)
    @Column(name = "FechaFinVigencia", nullable = false)
    private Date FechaFinVigencia;


    // Constructores
    public TasaInteres() {
    }

    public TasaInteres(Integer idTasaInteres) {
        IdTasaInteres = idTasaInteres;
    }

    // Getters y Setters
    public Integer getIdTasaInteres() {
        return IdTasaInteres;
    }

    public void setIdTasaInteres(Integer idTasaInteres) {
        IdTasaInteres = idTasaInteres;
    }

    public String getBaseCalculo() {
        return BaseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        BaseCalculo = baseCalculo;
    }

    public String getMetodoCalculo() {
        return MetodoCalculo;
    }

    public void setMetodoCalculo(String metodoCalculo) {
        MetodoCalculo = metodoCalculo;
    }

    public String getFrecuenciaCapitalizacion() {
        return FrecuenciaCapitalizacion;
    }

    public void setFrecuenciaCapitalizacion(String frecuenciaCapitalizacion) {
        FrecuenciaCapitalizacion = frecuenciaCapitalizacion;
    }

    public Instant getEstado() {
        return Estado;
    }

    public void setEstado(Instant estado) {
        Estado = estado;
    }

    public Date getFechaInicioVigencia() {
        return FechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        FechaInicioVigencia = fechaInicioVigencia;
    }

    public Date getFechaFinVigencia() {
        return FechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        FechaFinVigencia = fechaFinVigencia;
    }

    // Métodos hashCode y equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdTasaInteres == null) ? 0 : IdTasaInteres.hashCode());
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
        TasaInteres other = (TasaInteres) obj;
        if (IdTasaInteres == null) {
            if (other.IdTasaInteres != null)
                return false;
        } else if (!IdTasaInteres.equals(other.IdTasaInteres))
            return false;
        return true;
    }

    // Método toString
    @Override
    public String toString() {
        return "TasaInteres [IdTasaInteres=" + IdTasaInteres + ", BaseCalculo=" + BaseCalculo + ", MetodoCalculo="
                + MetodoCalculo + ", FrecuenciaCapitalizacion=" + FrecuenciaCapitalizacion + ", Estado=" + Estado
                + ", FechaInicioVigencia=" + FechaInicioVigencia + ", FechaFinVigencia=" + FechaFinVigencia + "]";
    }

}
