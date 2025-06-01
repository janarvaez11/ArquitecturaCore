package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import com.banquito.core.aplicacion.general.modelo.Sucursal;

@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCliente", nullable = false)
    private Integer id;

    @Column(name = "TipoEntidad", length = 10, nullable = false)
    private String tipoEntidad;

    @Column(name = "IdEntidad", nullable = false)
    private Integer idEntidad;

    @Column(name = "Nacionalidad", length = 2)
    private String nacionalidad;

    @ManyToOne
    @JoinColumn(name = "IdSucursal", nullable = false)
    private Sucursal sucursal;

    @Column(name = "TipoIdentificacion", length = 10)
    private String tipoIdentificacion;

    @Column(name = "NumeroIdentificacion", length = 20)
    private String numeroIdentificacion;

    @Column(name = "TipoCliente", length = 20)
    private String tipoCliente;

    @Column(name = "Segmento", length = 20)
    private String segmento;

    @Column(name = "CanalAfilicacion", length = 50)
    private String canalAfilicacion;

    @Column(name = "Nombre", length = 50)
    private String nombre;

    @Column(name = "FechaCreacion")
    private Timestamp fechaCreacion;

    @Column(name = "FechaActivacion")
    private Timestamp fechaActivacion;

    @Column(name = "Estado", length = 15)
    private String estado;

    @Column(name = "FechaCierre")
    private Timestamp fechaCierre;

    @Column(name = "FechaActualizacion")
    private Timestamp fechaActualizacion;

    @Column(name = "Comentarios", length = 100)
    private String comentarios;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private ContactoTransaccionCliente contacto;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<TelefonoCliente> telefonos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<DireccionCliente> direcciones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getCanalAfilicacion() {
        return canalAfilicacion;
    }

    public void setCanalAfilicacion(String canalAfilicacion) {
        this.canalAfilicacion = canalAfilicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Timestamp fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public ContactoTransaccionCliente getContacto() {
        return contacto;
    }

    public void setContacto(ContactoTransaccionCliente contacto) {
        this.contacto = contacto;
    }

    public List<TelefonoCliente> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<TelefonoCliente> telefonos) {
        this.telefonos = telefonos;
    }

    public List<DireccionCliente> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionCliente> direcciones) {
        this.direcciones = direcciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cliente cliente = (Cliente) o;
        return id != null && id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // toString
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", tipoEntidad='" + tipoEntidad + '\'' +
                ", idEntidad=" + idEntidad +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", tipoCliente='" + tipoCliente + '\'' +
                ", segmento='" + segmento + '\'' +
                ", canalAfilicacion='" + canalAfilicacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

}
