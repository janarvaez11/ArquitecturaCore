package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TelefonoCliente")
public class TelefonoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTelefonoCliente", nullable = false)
    private Integer idTelefonoCliente;

    @ManyToOne
    @JoinColumn(name = "IdCliente", nullable = false)
    private Cliente cliente;

    @Column(name = "TipoTelefono", length = 10)
    private String tipoTelefono;

    @Column(name = "NumeroTelefono", length = 10)
    private String numeroTelefono;

    @Column(name = "Estado", length = 10)
    private String estado;

    @Column(name = "FechaCreacion")
    private Date fechaCreacion;

    @Column(name = "FechaActualizacion")
    private Date fechaActualizacion;

    public TelefonoCliente() {
    }

    public TelefonoCliente(Integer idTelefonoCliente) {
        this.idTelefonoCliente = idTelefonoCliente;
    }

    public Integer getIdTelefonoCliente() {
        return idTelefonoCliente;
    }

    public void setIdTelefonoCliente(Integer idTelefonoCliente) {
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TelefonoCliente))
            return false;
        TelefonoCliente that = (TelefonoCliente) o;
        return Objects.equals(idTelefonoCliente, that.idTelefonoCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTelefonoCliente);
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
