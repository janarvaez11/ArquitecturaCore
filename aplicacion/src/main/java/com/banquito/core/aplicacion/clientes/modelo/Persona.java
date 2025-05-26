package com.banquito.core.aplicacion.clientes.modelo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name="Personas")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPersona", nullable = false)
    private Integer IdPersona;

    @Column(name = "TipoIdentificacion", length = 10, nullable = false)
    private String TipoIdentificación;

    @Column(name = "NumeroIdentificacion", length = 10, nullable = false)
    private String NumeroIdentificacion;

    @Column(name = "Nombres", length = 50, nullable = false)
    private String Nombres;

    @Column(name = "Genero", length = 10, nullable = false)
    private String Genero;

    @Column(name = "FechaNacimiento", nullable = false)
    private Date FechaNacimiento;

    @Column(name = "Nacionalidad", length = 25, nullable = false)
    private String Nacionalidad;

    @Column(name = "EstadoCivil", length = 15, nullable = false)
    private String EstadoCivil;

    @Column(name = "NivelEstudio", length = 30, nullable = false)
    private String NivelEstudio;

    @Column(name = "CorreoElectronico", length = 100, nullable = false)
    private String CorreoElectronico;

    @Column(name = "Estado", length = 15, nullable = false)
    private String Estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaRegistro", nullable = false)
    private Date FechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FechaActualizacion", nullable = false)
    private Date FechaActualizacion;

    public Persona() {
    }

    public Persona(Integer IdPersona) {
        this.IdPersona = IdPersona;
    }

    public Integer getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(Integer IdPersona) {
        this.IdPersona = IdPersona;
    }

    public String getTipoIdentificación() {
        return TipoIdentificación;
    }

    public void setTipoIdentificación(String tipoIdentificación) {
        TipoIdentificación = tipoIdentificación;
    }

    public String getNumeroIdentificacion() {
        return NumeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        NumeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getEstadoCivil() {
        return EstadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        EstadoCivil = estadoCivil;
    }

    public String getNivelEstudio() {
        return NivelEstudio;
    }

    public void setNivelEstudio(String nivelEstudio) {
        NivelEstudio = nivelEstudio;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Date getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return FechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        FechaActualizacion = fechaActualizacion;
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
        if (IdPersona == null) {
            if (other.IdPersona != null)
                return false;
        } else if (!IdPersona.equals(other.IdPersona))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdPersona == null) ? 0 : IdPersona.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + IdPersona +
                ", TipoIdentificación='" + TipoIdentificación + '\'' +
                ", NumeroIdentificacion='" + NumeroIdentificacion + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", Genero='" + Genero + '\'' +
                ", FechaNacimiento=" + FechaNacimiento +
                ", Nacionalidad='" + Nacionalidad + '\'' +
                ", EstadoCivil='" + EstadoCivil + '\'' +
                ", NivelEstudio=" + NivelEstudio +
                ", CorreoElectronico=" + CorreoElectronico +
                ", Estado=" + Estado +
                ", FechaRegistro=" + FechaRegistro +
                ", FechaActualizacion=" + FechaActualizacion +
                '}';
    }
}
