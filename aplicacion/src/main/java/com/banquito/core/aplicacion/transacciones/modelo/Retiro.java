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
@Table(name = "retiros")
public class Retiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retiro_id", nullable = false)
    private Integer retiroId;

    @Column(name = "tipo_transaccion_id", nullable = false)
    private Integer tipoTransaccionId;

    @Column(name = "transaccion_id", nullable = false)
    private Integer transaccionId;

    @Column(name = "cuenta_origen_id", nullable = false)
    private Integer cuentaOrigenId;

    @Column(name = "comision", precision = 18, scale = 2, nullable = false)
    private java.math.BigDecimal comision;

    @ManyToOne
    @JoinColumn(name = "tipo_transaccion_id", insertable = false, updatable = false)
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "transaccion_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    @Version
    @Column(name = "version")
    private Long version;

    public Retiro() {
    }

    public Retiro(Integer retiroId) {
        this.retiroId = retiroId;
    }

    public Integer getRetiroId() {
        return retiroId;
    }

    public void setRetiroId(Integer retiroId) {
        this.retiroId = retiroId;
    }

    public Integer getTipoTransaccionId() {
        return tipoTransaccionId;
    }

    public void setTipoTransaccionId(Integer tipoTransaccionId) {
        this.tipoTransaccionId = tipoTransaccionId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Integer getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Integer cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public java.math.BigDecimal getComision() {
        return comision;
    }

    public void setComision(java.math.BigDecimal comision) {
        this.comision = comision;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccion tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
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
        result = prime * result + ((retiroId == null) ? 0 : retiroId.hashCode());
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
        Retiro other = (Retiro) obj;
        if (retiroId == null) {
            if (other.retiroId != null)
                return false;
        } else if (!retiroId.equals(other.retiroId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Retiro [retiroId=" + retiroId + ", tipoTransaccionId=" + tipoTransaccionId + ", transaccionId="
                + transaccionId + ", cuentaOrigenId=" + cuentaOrigenId + ", comision=" + comision + ", version="
                + version + "]";
    }
}
