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
@Table(name = "cheques")
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheque_id", nullable = false)
    private Integer chequeId;

    @Column(name = "transaccion_id", nullable = false)
    private Integer transaccionId;

    @Column(name = "numero_cheque", length = 20, nullable = false)
    private String numeroCheque;

    @Column(name = "nombre_beneficiario", length = 100, nullable = false)
    private String nombreBeneficiario;

    @Column(name = "fecha_emision", nullable = false)
    private java.sql.Date fechaEmision;

    @Column(name = "fecha_cobro", nullable = false)
    private java.sql.Date fechaCobro;

    @ManyToOne
    @JoinColumn(name = "transaccion_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    @Version
    @Column(name = "version")
    private Long version;

    public Cheque() {
    }

    public Cheque(Integer chequeId) {
        this.chequeId = chequeId;
    }

    public Integer getChequeId() {
        return chequeId;
    }

    public void setChequeId(Integer chequeId) {
        this.chequeId = chequeId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public java.sql.Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(java.sql.Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public java.sql.Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(java.sql.Date fechaCobro) {
        this.fechaCobro = fechaCobro;
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
        result = prime * result + ((chequeId == null) ? 0 : chequeId.hashCode());
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
        Cheque other = (Cheque) obj;
        if (chequeId == null) {
            if (other.chequeId != null)
                return false;
        } else if (!chequeId.equals(other.chequeId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cheque [chequeId=" + chequeId + ", transaccionId=" + transaccionId + ", numeroCheque=" + numeroCheque
                + ", nombreBeneficiario=" + nombreBeneficiario + ", fechaEmision=" + fechaEmision + ", fechaCobro="
                + fechaCobro + ", version=" + version + "]";
    }
}
