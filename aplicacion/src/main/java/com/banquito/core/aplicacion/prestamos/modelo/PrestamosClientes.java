package com.banquito.core.aplicacion.prestamos.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "PrestamosClientes")
public class PrestamosClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PrestamosClientes_id_gen")
    @Column(name = "IdPrestamoCliente", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "IdPrestamo")
    private Prestamos idPrestamo;

    @Column(name = "Estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "FechaAprobacion")
    private Instant fechaAprobacion;

    @Column(name = "FechaDesembolso")
    private Instant fechaDesembolso;

    @Column(name = "FechaVencimiento")
    private Instant fechaVencimiento;

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

    public Prestamos getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Prestamos idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Instant fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Instant getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Instant fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public Instant getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Instant fechaVencimiento) {
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
        return "PrestamosClientes{" +
                "id=" + id +
                ", idPrestamo=" + idPrestamo +
                ", estado='" + estado + '\'' +
                ", fechaAprobacion=" + fechaAprobacion +
                ", fechaDesembolso=" + fechaDesembolso +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}