package com.banquito.core.aplicacion.transacciones.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaccion_id", nullable = false)
    private Integer transaccionId;

    @Column(name = "tipo_transaccion_id", nullable = false)
    private Integer tipoTransaccionId;

    @Column(name = "cuenta_id", nullable = false)
    private Integer cuentaId;

    @Column(name = "estado_id", nullable = false)
    private String estadoId;

    @Column(name = "monto", precision = 18, scale = 2, nullable = false)
    private java.math.BigDecimal monto;

    @Column(name = "fecha_transaccion", nullable = false)
    private java.sql.Date fechaTransaccion;

    @Column(name = "fecha_contable", nullable = false)
    private java.sql.Date fechaContable;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "creado_en", nullable = false)
    private java.sql.Timestamp creadoEn;

    @ManyToOne
    @JoinColumn(name = "tipo_transaccion_id", insertable = false, updatable = false)
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "estado_id", insertable = false, updatable = false)
    private EstadoTransaccion estadoTransaccion;

    @Version
    @Column(name = "version")
    private Long version;

    public Transaccion() {
    }

    public Transaccion(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Integer getTipoTransaccionId() {
        return tipoTransaccionId;
    }

    public void setTipoTransaccionId(Integer tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public java.math.BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(java.math.BigDecimal monto) {
        this.monto = monto;
    }

    public java.sql.Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(java.sql.Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public java.sql.Date getFechaContable() {
        return fechaContable;
    }

    public void setFechaContable(java.sql.Date fechaContable) {
        this.fechaContable = fechaContable;
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

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public EstadoTransaccion getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(EstadoTransaccion estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
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
        result = prime * result + ((transaccionId == null) ? 0 : transaccionId.hashCode());
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
        Transaccion other = (Transaccion) obj;
        if (transaccionId == null) {
            if (other.transaccionId != null)
                return false;
        } else if (!transaccionId.equals(other.transaccionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transaccion [transaccionId=" + transaccionId + ", tipoTransaccionId=" + tipoTransaccionId
                + ", cuentaId=" + cuentaId + ", estadoId=" + estadoId + ", monto=" + monto + ", fechaTransaccion="
                + fechaTransaccion + ", fechaContable=" + fechaContable + ", descripcion=" + descripcion
                + ", creadoEn=" + creadoEn + ", version=" + version + "]";
    }
}
