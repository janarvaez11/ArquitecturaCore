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
@Table(name = "pago_tarjeta_debito")
public class PagoTarjetaDebito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id", nullable = false)
    private Integer pagoId;

    @Column(name = "transaccion_id", nullable = false)
    private Integer transaccionId;

    @Column(name = "numero_tarjeta", length = 16, nullable = false)
    private String numeroTarjeta;

    @Column(name = "nombre_comercio", length = 100, nullable = false)
    private String nombreComercio;

    @Column(name = "codigo_categoria_comercio", length = 10, nullable = false)
    private String codigoCategoriaComercio;

    @ManyToOne
    @JoinColumn(name = "transaccion_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    @Version
    @Column(name = "version")
    private Long version;

    public PagoTarjetaDebito() {
    }

    public PagoTarjetaDebito(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getCodigoCategoriaComercio() {
        return codigoCategoriaComercio;
    }

    public void setCodigoCategoriaComercio(String codigoCategoriaComercio) {
        this.codigoCategoriaComercio = codigoCategoriaComercio;
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
        result = prime * result + ((pagoId == null) ? 0 : pagoId.hashCode());
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
        PagoTarjetaDebito other = (PagoTarjetaDebito) obj;
        if (pagoId == null) {
            if (other.pagoId != null)
                return false;
        } else if (!pagoId.equals(other.pagoId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PagoTarjetaDebito [pagoId=" + pagoId + ", transaccionId=" + transaccionId + ", numeroTarjeta="
                + numeroTarjeta + ", nombreComercio=" + nombreComercio + ", codigoCategoriaComercio="
                + codigoCategoriaComercio + ", version=" + version + "]";
    }
}
