package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="Representantes")
public class Representante {

    @EmbeddedId
    private RepresentanteId id;

    @ManyToOne
    @MapsId("IdEmpresa")
    @JoinColumn(name = "IdEmpresa", referencedColumnName = "IdEmpresa")
    private Empresa IdEmpresa;

    @ManyToOne
    @MapsId("IdCliente")
    @JoinColumn(name = "IdCliente", referencedColumnName = "IdCliente")
    private Cliente IdCliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaAsignacion")
    private Date FechaAsignacion;

    @Column(name = "Estado", length = 15, nullable = false)
    private String Estado;

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
        return IdEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public Cliente getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        IdCliente = idCliente;
    }

    public Date getFechaAsignacion() {
        return FechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        FechaAsignacion = fechaAsignacion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
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
                ", IdEmpresa=" + IdEmpresa +
                ", IdCliente=" + IdCliente +
                ", FechaAsignacion=" + FechaAsignacion +
                ", Estado='" + Estado + '\'' +
                '}';
    }
}
