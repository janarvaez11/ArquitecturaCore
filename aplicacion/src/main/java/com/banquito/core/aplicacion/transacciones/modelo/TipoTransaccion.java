package com.banquito.core.aplicacion.transacciones.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "tipo_transaccion")
public class TipoTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_transaccion_id", nullable = false)
    private Integer tipoTransaccionId;

    @Column(name = "comision_id", nullable = false)
    private Integer comisionId;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    @Column(name = "creado_en", nullable = false)
    private java.sql.Timestamp creadoEn;

    @Version
    @Column(name = "version")
    private Long version;

    public TipoTransaccion() {
    }

    public TipoTransaccion(Integer tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public Integer getTipoTransaccionId() {
        return tipoTransaccionId;
    }

    public void setTipoTransaccionId(Integer tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public Integer getComisionId() {
        return comisionId;
    }

    public void setComisionId(Integer comisionId) {
        this.comisionId = comisionId;
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
        result = prime * result + ((tipoTransaccionId == null) ? 0 : tipoTransaccionId.hashCode());
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
        TipoTransaccion other = (TipoTransaccion) obj;
        if (tipoTransaccionId == null) {
            if (other.tipoTransaccionId != null)
                return false;
        } else if (!tipoTransaccionId.equals(other.tipoTransaccionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TipoTransaccion [tipoTransaccionId=" + tipoTransaccionId + ", comisionId=" + comisionId + ", nombre="
                + nombre + ", descripcion=" + descripcion + ", creadoEn=" + creadoEn + ", version=" + version + "]";
    }
}
