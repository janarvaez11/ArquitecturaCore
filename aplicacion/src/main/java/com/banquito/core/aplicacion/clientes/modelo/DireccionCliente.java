package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Date;

@Entity
@Table(name = "direccioncliente")
public class DireccionCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDireccion", nullable = false)
    private Integer idDireccion;

    @ManyToOne
    @JoinColumn(name = "IdCliente", nullable = false)
    private Cliente cliente;

    @Column(name = "Tipo", length = 30)
    private String tipo;

    @Column(name = "Estado", length = 20)
    private String estado;

    @Column(name = "Linea1", length = 150)
    private String linea1;

    @Column(name = "Linea2", length = 150)
    private String linea2;

    @Column(name = "CodigoPostal", length = 10)
    private String codigoPostal;

    @Column(name = "FechaCreacion")
    private Date fechaCreacion;

    @Column(name = "FechaActualizacion")
    private Date fechaActualizacion;


    public DireccionCliente() {}

    public DireccionCliente(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLinea1() {
        return linea1;
    }

    public void setLinea1(String linea1) {
        this.linea1 = linea1;
    }

    public String getLinea2() {
        return linea2;
    }

    public void setLinea2(String linea2) {
        this.linea2 = linea2;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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
    public int hashCode() {
        return Objects.hash(idDireccion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DireccionCliente)) return false;
        DireccionCliente other = (DireccionCliente) obj;
        return Objects.equals(idDireccion, other.idDireccion);
    }

    @Override
    public String toString() {
        return "DireccionCliente{idDireccion=" + idDireccion + "}";
    }
}

