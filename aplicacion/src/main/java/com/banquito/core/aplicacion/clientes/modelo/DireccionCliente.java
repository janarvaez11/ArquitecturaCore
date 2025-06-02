package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "DireccionCliente")
public class DireccionCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDireccionCliente")
    private Integer idDireccionCliente;

    @ManyToOne
    @JoinColumn(name = "IdCliente")
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
    private Timestamp fechaCreacion;

    @Column(name = "FechaActualizacion")
    private Timestamp fechaActualizacion;

    public Integer getidDireccion() {
        return idDireccionCliente;
    }

    public void setidDireccion(Integer idDireccionCliente) {
        this.idDireccionCliente = idDireccionCliente;
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
        DireccionCliente that = (DireccionCliente) o;
        return idDireccionCliente != null && idDireccionCliente.equals(that.idDireccionCliente);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DireccionCliente{" +
                "idDireccion=" + idDireccionCliente +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", linea1='" + linea1 + '\'' +
                ", linea2='" + linea2 + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                '}';
    }

}
