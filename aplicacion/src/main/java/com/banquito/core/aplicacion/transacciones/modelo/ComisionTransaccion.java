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
@Table(name = "comision_transaccion")
public class ComisionTransaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comision_id", nullable = false)
    private Integer comisionId;

    @Column(name = "tipo_transaccion_id", nullable = false)
    private Integer tipoTransaccionId;

    @Column(name = "tipo_comision", nullable = false)
    private String tipoComision;

    @Column(name = "monto", precision = 18, scale = 2, nullable = false)
    private java.math.BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "tipo_transaccion_id", insertable = false, updatable = false)
    private TipoTransaccion tipoTransaccion;

    @Version
    @Column(name = "version")
    private Long version;

    public ComisionTransaccion() {
    }

    public ComisionTransaccion(Integer comisionId) {
        this.comisionId = comisionId;
    }

    public Integer getComisionId() {
        return comisionId;
    }

    public void setComisionId(Integer comisionId) {
        this.comisionId = comisionId;
    }

    public Integer getTipoTransaccionId() {
        return tipoTransaccionId;
    }

    public void setTipoTransaccionId(Integer tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public String getTipoComision() {
        return tipoComision;
    }

    public void setTipoComision(String tipoComision) {
        this.tipoComision = tipoComision;
    }

    public java.math.BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(java.math.BigDecimal monto) {
        this.monto = monto;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
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
        result = prime * result + ((comisionId == null) ? 0 : comisionId.hashCode());
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
        ComisionTransaccion other = (ComisionTransaccion) obj;
        if (comisionId == null) {
            if (other.comisionId != null)
                return false;
        } else if (!comisionId.equals(other.comisionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComisionTransaccion [comisionId=" + comisionId + ", tipoTransaccionId=" + tipoTransaccionId
                + ", tipoComision=" + tipoComision + ", monto=" + monto + ", version=" + version + "]";
    }
}
