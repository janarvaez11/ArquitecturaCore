package com.banquito.core.aplicacion.transacciones.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "transferencias")
public class Transferencia {

    @EmbeddedId
    private TransferenciaId id;

    @Column(name = "comision", precision = 18, scale = 2, nullable = false)
    private java.math.BigDecimal comision;

    @Column(name = "es_interbancaria", nullable = false)
    private Boolean esInterbancaria;

    @ManyToOne
    @JoinColumn(name = "tipo_transaccion_id", insertable = false, updatable = false)
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "transaccion_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    @Version
    @Column(name = "version")
    private Long version;

    public Transferencia() {
    }

    public Transferencia(TransferenciaId id) {
        this.id = id;
    }

    public TransferenciaId getId() {
        return id;
    }

    public void setId(TransferenciaId id) {
        this.id = id;
    }

    public java.math.BigDecimal getComision() {
        return comision;
    }

    public void setComision(java.math.BigDecimal comision) {
        this.comision = comision;
    }

    public Boolean getEsInterbancaria() {
        return esInterbancaria;
    }

    public void setEsInterbancaria(Boolean esInterbancaria) {
        this.esInterbancaria = esInterbancaria;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Transferencia other = (Transferencia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transferencia [id=" + id + ", comision=" + comision + ", esInterbancaria=" + esInterbancaria
                + ", version=" + version + "]";
    }
}
