package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "contactotransaccioncliente")
public class ContactoTransaccionCliente {

    @Id
    private Integer idCliente;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    private String telefono;
    private String correoElectronico;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public ContactoTransaccionCliente() {}

    public ContactoTransaccionCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ContactoTransaccionCliente)) return false;
        ContactoTransaccionCliente other = (ContactoTransaccionCliente) obj;
        return Objects.equals(idCliente, other.idCliente);
    }

    @Override
    public String toString() {
        return "ContactoTransaccionCliente{idCliente=" + idCliente + "}";
    }
}

