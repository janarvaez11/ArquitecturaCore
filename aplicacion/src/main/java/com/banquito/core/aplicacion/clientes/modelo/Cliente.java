package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Date;
import java.util.List;

import com.banquito.core.aplicacion.general.modelo.Pais;
import com.banquito.core.aplicacion.general.modelo.Sucursal;

@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCliente", nullable = false)
    private Integer idCliente;

    @Column(name = "TipoEntidad", nullable = false, length = 10)
    private String tipoEntidad;

    @Column(name = "IdEntidad", nullable = false)
    private Integer idEntidad;

    @ManyToOne
    @JoinColumn(name = "IdPais", referencedColumnName = "IdPais", nullable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "IdSucursal", referencedColumnName = "IdSucursal", nullable = false)
    private Sucursal sucursal; // Relación con Sucursales

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
    private Date fechaCreacion;

    @Column(name = "FechaActivacion")
    private Date fechaActivacion;

    @Column(name = "Estado", length = 15)
    private String estado;

    @Column(name = "FechaCierre")
    private Date fechaCierre;

    @Column(name = "FechaActualizacion")
    private Date fechaActualizacion;

    @Column(name = "Comentarios", length = 100)
    private String comentarios;

    @OneToMany(mappedBy = "cliente")
    private List<TelefonoCliente> telefonos;

    @OneToMany(mappedBy = "cliente")
    private List<DireccionCliente> direcciones;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private ContactoTransaccionCliente contacto;

    public Cliente() {}

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cliente)) return false;
        Cliente other = (Cliente) obj;
        return Objects.equals(idCliente, other.idCliente);
    }

    @Override
    public String toString() {
        return "Cliente{idCliente=" + idCliente + ", tipoEntidad='" + tipoEntidad + "'}";
    }
}
