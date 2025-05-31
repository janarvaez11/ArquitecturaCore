package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name="Empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEmpresa", nullable = false)
    private Integer idEmpresa;

    @Column(name = "TipoIdentificacion", length = 10, nullable = false)
    private String tipoIdentificacion;

    @Column(name = "NumeroIdentificacion",  length = 20, nullable = false)
    private String numeroIdentificacion;

    @Column(name = "NombreEmpresa",  length = 25, nullable = false)
    private String nombreEmpresa;

    @Column(name = "RazonSocial",  length = 25, nullable = false)
    private String razonSocial;

    @Column(name = "fechaConstitucion", nullable = false)
    private Date fechaConstitucion;

    @Column(name = "CorreoElectronico",  length = 40, nullable = false)
    private String correoElectronico;

    @Column(name = "tipoCompania",  length = 20, nullable = false)
    private String tipoCompania;

    @Column(name = "Estado",  length = 20, nullable = false)
    private String estado;

    @Column(name = "SectorEconomico",  length = 20, nullable = false)
    private String sectorEconomico;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaRegistro", nullable = false)
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaActualizacion", nullable = false)
    private Date fechaActualizacion;

    public Empresa() {}

    public Empresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(Date fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTipoCompania() {
        return tipoCompania;
    }

    public void setTipoCompania(String tipoCompania) {
        this.tipoCompania = tipoCompania;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSectorEconomico() {
        return sectorEconomico;
    }

    public void setSectorEconomico(String sectorEconomico) {
        this.sectorEconomico = sectorEconomico;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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
        if (idEmpresa == null) {
            if (other.idEmpresa != null)
                return false;
        } else if (!idEmpresa.equals(other.idEmpresa))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "idEmpresa=" + idEmpresa +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", fechaConstitucion=" + fechaConstitucion +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", tipoCompania='" + tipoCompania + '\'' +
                ", estado='" + estado + '\'' +
                ", sectorEconomico='" + sectorEconomico + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
