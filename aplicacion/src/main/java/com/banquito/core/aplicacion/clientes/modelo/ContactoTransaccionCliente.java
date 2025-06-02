package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Date;

@Entity
@Table(name = "ContactoTransaccionCliente")
public class ContactoTransaccionCliente {

    @Id
    @Column(name = "idContactoTransaccionCliente")
    private Integer idContactoTransaccionCliente;

    @OneToOne
    @MapsId
    @JoinColumn(name = "IdCliente")
    private Cliente cliente;

    @Column(name = "Telefono", length = 10)
    private String telefono;

    @Column(name = "CorreoElectronico", length = 40)
    private String correoElectronico;

    @Column(name = "FechaCreacion")
    private Date fechaCreacion;

    @Column(name = "FechaActualizacion")
    private Date fechaActualizacion;
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
        if (o == null || getClass() != o.getClass())
            return false;
        ContactoTransaccionCliente that = (ContactoTransaccionCliente) o;
        return id != null && id.equals(that.id);
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
        return "ContactoTransaccionCliente{" +
                "id=" + id +
                ", telefono='" + telefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }
}

