package com.banquito.core.aplicacion.prestamos.modelo;

import jakarta.persistence.*;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PrestamosClientes")
public class PrestamosClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPrestamoCliente", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdCliente")
    private Cliente idCliente;

    @ManyToOne
    @JoinColumn(name = "IdPrestamo")
    private Prestamo idPrestamo;

    @Column(name = "Estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "FechaAprobacion")
    private LocalDateTime fechaAprobacion;

    @Column(name = "FechaDesembolso")
    private LocalDateTime fechaDesembolso;

    @Column(name = "FechaVencimiento")
    private LocalDateTime fechaVencimiento;

    public PrestamosClientes() {
    }

    public PrestamosClientes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Prestamo getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Prestamo idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public LocalDateTime getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(LocalDateTime fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PrestamosClientes that = (PrestamosClientes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrestamosClientes [id=" + id + ", idCliente=" + idCliente + ", idPrestamo=" + idPrestamo + ", estado="
                + estado + ", fechaAprobacion=" + fechaAprobacion + ", fechaDesembolso=" + fechaDesembolso
                + ", fechaVencimiento=" + fechaVencimiento + "]";
    }

}