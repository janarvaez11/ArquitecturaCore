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
    @MapsId("idEmpresa")
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    private Empresa idEmpresa;

    @ManyToOne
    @MapsId("idCliente")
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    private Cliente idCliente;

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

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
                ", idEmpresa=" + idEmpresa +
                ", idCliente=" + idCliente +
                ", fechaAsignacion=" + fechaAsignacion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
