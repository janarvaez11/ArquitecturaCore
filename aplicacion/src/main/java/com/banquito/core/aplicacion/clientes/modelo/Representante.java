package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="Representantes")
public class Representante {

    @EmbeddedId
    private RepresentanteId id;

    @ManyToOne
    @MapsId("empresa")
    @JoinColumn(name = "IdEmpresa", referencedColumnName = "IdEmpresa")
    private Empresa empresa;

    @ManyToOne
    @MapsId("cliente")
    @JoinColumn(name = "IdCliente", referencedColumnName = "IdCliente")
    private Cliente cliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAsignacion")
    private Date fechaAsignacion;

    @Column(name = "estado", length = 15, nullable = false)
    private String estado;

    public Representante() {}

    public Representante(RepresentanteId id) {
        this.id = id;
    }

    public RepresentanteId getId() {
        return id;
    }

    public void setId(RepresentanteId id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Representante that = (Representante) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Representante{" +
                "id=" + id +
                ", empresa=" + empresa +
                ", cliente=" + cliente +
                ", fechaAsignacion=" + fechaAsignacion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
