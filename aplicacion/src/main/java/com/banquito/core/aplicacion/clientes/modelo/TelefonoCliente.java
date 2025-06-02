package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TelefonoCliente")
public class TelefonoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTelefonoCliente")
    private Integer idTelefonoCliente;

    @ManyToOne
    @JoinColumn(name = "IdCliente")
    private Cliente cliente;

    @Column(name = "TipoTelefono", length = 10)
    private String tipoTelefono;

    @Column(name = "NumeroTelefono", length = 10)
    private String numeroTelefono;

    @Column(name = "Estado", length = 10)
    private String estado;

    @Column(name = "FechaCreacion")
    private Timestamp fechaCreacion;

    @Column(name = "FechaActualizacion")
    private Timestamp fechaActualizacion;

    public Integer getId() {
        return idTelefonoCliente;
    }

    public void setId(Integer idTelefonoCliente) {
        this.idTelefonoCliente = idTelefonoCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TelefonoCliente that = (TelefonoCliente) o;
        return idTelefonoCliente != null && idTelefonoCliente.equals(that.idTelefonoCliente);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TelefonoCliente{" +
                "idTelefonoCliente=" + idTelefonoCliente +
                ", tipoTelefono='" + tipoTelefono + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

}
