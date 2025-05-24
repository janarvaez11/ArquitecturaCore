package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name="Empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEmpresa", nullable = false)
    private Integer IdEmpresa;

    @Column(name = "TipoIdentificacion", length = 10, nullable = false)
    private String TipoIdentificacion;

    @Column(name = "NumeroIdentificacion",  length = 20, nullable = false)
    private String NumeroIdentificacion;

    @Column(name = "NombreEmpresa",  length = 25, nullable = false)
    private String NombreEmpresa;

    @Column(name = "RazonSocial",  length = 25, nullable = false)
    private String RazonSocial;

    @Column(name = "FechaConstitucion", nullable = false)
    private Date FechaConstitucion;

    @Column(name = "CorreoElectronico",  length = 40, nullable = false)
    private String CorreoElectronico;

    @Column(name = "TipoCompania",  length = 20, nullable = false)
    private String TipoCompania;

    @Column(name = "Estado",  length = 20, nullable = false)
    private String Estado;

    @Column(name = "SectorEconomico",  length = 20, nullable = false)
    private String SectorEconomico;

    @Column(name = "FechaRegistro")
    private Instant FechaRegistro;

    @Column(name = "FechaActualizacion")
    private Instant FechaActualizacion;

    public Empresa() {}

    public Empresa(Integer IdEmpresa) {
        this.IdEmpresa = IdEmpresa;
    }

    public Integer getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public String getTipoIdentificacion() {
        return TipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        TipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return NumeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        NumeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreEmpresa() {
        return NombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        NombreEmpresa = nombreEmpresa;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public Date getFechaConstitucion() {
        return FechaConstitucion;
    }

    public void setFechaConstitucion(Date fechaConstitucion) {
        FechaConstitucion = fechaConstitucion;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }

    public String getTipoCompania() {
        return TipoCompania;
    }

    public void setTipoCompania(String tipoCompania) {
        TipoCompania = tipoCompania;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getSectorEconomico() {
        return SectorEconomico;
    }

    public void setSectorEconomico(String sectorEconomico) {
        SectorEconomico = sectorEconomico;
    }

    public Instant getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public Instant getFechaActualizacion() {
        return FechaActualizacion;
    }

    public void setFechaActualizacion(Instant fechaActualizacion) {
        FechaActualizacion = fechaActualizacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Empresa other = (Empresa) obj;
        if (IdEmpresa == null) {
            if (other.IdEmpresa != null)
                return false;
        } else if (!IdEmpresa.equals(other.IdEmpresa))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdEmpresa == null) ? 0 : IdEmpresa.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "IdEmpresa=" + IdEmpresa +
                ", TipoIdentificacion='" + TipoIdentificacion + '\'' +
                ", NumeroIdentificacion='" + NumeroIdentificacion + '\'' +
                ", NombreEmpresa='" + NombreEmpresa + '\'' +
                ", RazonSocial='" + RazonSocial + '\'' +
                ", FechaConstitucion=" + FechaConstitucion +
                ", CorreoElectronico='" + CorreoElectronico + '\'' +
                ", TipoCompania='" + TipoCompania + '\'' +
                ", Estado='" + Estado + '\'' +
                ", SectorEconomico='" + SectorEconomico + '\'' +
                ", FechaRegistro=" + FechaRegistro +
                ", FechaActualizacion=" + FechaActualizacion +
                '}';
    }
}
