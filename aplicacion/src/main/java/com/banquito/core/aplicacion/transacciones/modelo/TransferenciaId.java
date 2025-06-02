package com.banquito.core.aplicacion.transacciones.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TransferenciaId implements Serializable {

    @Column(name = "transferencia_id", nullable = false)
    private Integer transferenciaId;

    @Column(name = "tipo_transaccion_id", nullable = false)
    private Integer tipoTransaccionId;

    @Column(name = "transaccion_id", nullable = false)
    private Integer transaccionId;

    @Column(name = "cuenta_origen_id", nullable = false)
    private Integer cuentaOrigenId;

    @Column(name = "cuenta_destino_id", nullable = false)
    private Integer cuentaDestinoId;

    public TransferenciaId() {
    }

    public TransferenciaId(Integer transferenciaId, Integer tipoTransaccionId, Integer transaccionId, 
                          Integer cuentaOrigenId, Integer cuentaDestinoId) {
        this.transferenciaId = transferenciaId;
        this.tipoTransaccionId = tipoTransaccionId;
        this.transaccionId = transaccionId;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
    }

    public Integer getTransferenciaId() {
        return transferenciaId;
    }

    public void setTransferenciaId(Integer transferenciaId) {
        this.transferenciaId = transferenciaId;
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

    public Integer getCuentaDestinoId() {
        return cuentaDestinoId;
    }

    public void setCuentaDestinoId(Integer cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((transferenciaId == null) ? 0 : transferenciaId.hashCode());
        result = prime * result + ((tipoTransaccionId == null) ? 0 : tipoTransaccionId.hashCode());
        result = prime * result + ((transaccionId == null) ? 0 : transaccionId.hashCode());
        result = prime * result + ((cuentaOrigenId == null) ? 0 : cuentaOrigenId.hashCode());
        result = prime * result + ((cuentaDestinoId == null) ? 0 : cuentaDestinoId.hashCode());
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
        TransferenciaId other = (TransferenciaId) obj;
        if (transferenciaId == null) {
            if (other.transferenciaId != null)
                return false;
        } else if (!transferenciaId.equals(other.transferenciaId))
            return false;
        if (tipoTransaccionId == null) {
            if (other.tipoTransaccionId != null)
                return false;
        } else if (!tipoTransaccionId.equals(other.tipoTransaccionId))
            return false;
        if (transaccionId == null) {
            if (other.transaccionId != null)
                return false;
        } else if (!transaccionId.equals(other.transaccionId))
            return false;
        if (cuentaOrigenId == null) {
            if (other.cuentaOrigenId != null)
                return false;
        } else if (!cuentaOrigenId.equals(other.cuentaOrigenId))
            return false;
        if (cuentaDestinoId == null) {
            if (other.cuentaDestinoId != null)
                return false;
        } else if (!cuentaDestinoId.equals(other.cuentaDestinoId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TransferenciaId [transferenciaId=" + transferenciaId + ", tipoTransaccionId=" + tipoTransaccionId
                + ", transaccionId=" + transaccionId + ", cuentaOrigenId=" + cuentaOrigenId + ", cuentaDestinoId="
                + cuentaDestinoId + "]";
    }
} 