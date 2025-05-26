package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name="Personas")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPersona", nullable = false)
    private Integer idPersona;

    @Column(name = "TipoIdentificacion", length = 10, nullable = false)
    private String tipoIdentificación;

    @Column(name = "NumeroIdentificacion", length = 10, nullable = false)
    private String numeroIdentificacion;

    @Column(name = "Nombres", length = 50, nullable = false)
    private String nombres;

    @Column(name = "Genero", length = 10, nullable = false)
    private String genero;

    @Column(name = "fechaNacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "Nacionalidad", length = 25, nullable = false)
    private String nacionalidad;

    @Column(name = "EstadoCivil", length = 15, nullable = false)
    private String estadoCivil;

    @Column(name = "NivelEstudio", length = 30, nullable = false)
    private String nivelEstudio;

    @Column(name = "CorreoElectronico", length = 100, nullable = false)
    private String correoElectronico;

    @Column(name = "Estado", length = 15, nullable = false)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaRegistro", nullable = false)
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaActualizacion", nullable = false)
    private Date fechaActualizacion;

    public Persona() {
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipoIdentificación() {
        return tipoIdentificación;
    }

    public void setTipoIdentificación(String tipoIdentificación) {
        this.tipoIdentificación = tipoIdentificación;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNivelEstudio() {
        return nivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        if (idPersona == null) {
            if (other.idPersona != null)
                return false;
        } else if (!idPersona.equals(other.idPersona))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", tipoIdentificación='" + tipoIdentificación + '\'' +
                ", numeroIdentificacion='" + numeroIdentificacion + '\'' +
                ", nombres='" + nombres + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", nivelEstudio='" + nivelEstudio + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
