package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
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

    public ContactoTransaccionCliente() {
    }

    public ContactoTransaccionCliente(Integer idContactoTransaccionCliente) {
        this.idContactoTransaccionCliente = idContactoTransaccionCliente;
    }

    public Integer getIdContactoTransaccionCliente() {
        return idContactoTransaccionCliente;
    }

    public void setIdContactoTransaccionCliente(Integer idContactoTransaccionCliente) {
        this.idContactoTransaccionCliente = idContactoTransaccionCliente;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idContactoTransaccionCliente == null) ? 0 : idContactoTransaccionCliente.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContactoTransaccionCliente other = (ContactoTransaccionCliente) obj;
        if (idContactoTransaccionCliente == null) {
            if (other.idContactoTransaccionCliente != null)
                return false;
        } else if (!idContactoTransaccionCliente.equals(other.idContactoTransaccionCliente))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContactoTransaccionCliente [idContactoTransaccionCliente=" + idContactoTransaccionCliente + ", cliente="
                + cliente + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + ", fechaCreacion="
                + fechaCreacion + ", fechaActualizacion=" + fechaActualizacion + "]";
    }


    

    


}

