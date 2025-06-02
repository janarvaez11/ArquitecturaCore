package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ContactoTransaccionCliente")
public class ContactoTransaccionCliente {

    @Id
    @Column(name = "IdCliente")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "IdCliente")
    private Cliente cliente;

    @Column(name = "Telefono", length = 10)
    private String telefono;

    @Column(name = "CorreoElectronico", length = 40)
    private String correoElectronico;

    @Column(name = "FechaCreacion")
    private Timestamp fechaCreacion;

    @Column(name = "FechaActualizacion")
    private Timestamp fechaActualizacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        ContactoTransaccionCliente that = (ContactoTransaccionCliente) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
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
