package com.banquito.core.aplicacion.transacciones.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "estado_transaccion")
public class EstadoTransaccion {

    @Id
    @Column(name = "estado_id", nullable = false)
    private String estadoId;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "creado_en", nullable = false)
    private java.sql.Timestamp creadoEn;

    @Version
    @Column(name = "version")
    private Long version;

    public EstadoTransaccion() {
    }

    public EstadoTransaccion(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public java.sql.Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(java.sql.Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((estadoId == null) ? 0 : estadoId.hashCode());
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
        EstadoTransaccion other = (EstadoTransaccion) obj;
        if (estadoId == null) {
            if (other.estadoId != null)
                return false;
        } else if (!estadoId.equals(other.estadoId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EstadoTransaccion [estadoId=" + estadoId + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", creadoEn=" + creadoEn + ", version=" + version + "]";
    }
}
