package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "telefonocliente")
public class TelefonoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTelefonoCliente;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @Column(length = 10)
    private String tipoTelefono;

    @Column(length = 10)
    private String numeroTelefono;

    @Column(length = 10)
    private String estado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructor vacío
    public TelefonoCliente() {}

    // Constructor solo con la clave primaria
    public TelefonoCliente(Integer idTelefonoCliente) {
        this.idTelefonoCliente = idTelefonoCliente;
    }

    // Getters y Setters
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // equals y hashCode solo con ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefonoCliente)) return false;
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
