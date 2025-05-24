package com.banquito.core.aplicacion.cuentas.modelo;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TipoCuentas")

public class TipoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa IDENTITY para campos SERIAL en PostgreSQL
    @Column(name = "IdTipoCuenta", nullable = false)
    private Integer IdTipoCuenta;

    @Column(name = "IdTasaInteresPorDefecto", nullable = false)
    private Integer IdTasaInteresPorDefecto;

    @Column(name = "Nombre", length = 20, nullable = false)
    private String Nombre;

    @Column(name = "Descripcion", length = 50, nullable = false)
    private String Descripcion;

    @Column(name = "RequisitosApertura", length = 300, nullable = false)
    private String RequisitosApertura;

    @Column(name = "TipoCliente", length = 30, nullable = false)
    private String tipocliente;

    @Column(name = "CuentasContablesAsociadas", length = 50, nullable = false)
    private String CuentasContablesAsociadas;

    @Column(name = "Estado", length = 20, nullable = false)
    private String Estado;

    @Column(name = "FechaCreacion")
    private Instant FechaCreacion;

    @Column(name = "FechaModificacion")
    private Instant FechaModificacion;


    //relacion a la tabla TasaInteres
    @ManyToOne
    @JoinColumn(name = "IdTasaInteresPorDefecto", referencedColumnName = "IdTasaInteres")
    private TasaInteres tasaInteres;



    // Constructores
    public TipoCuenta() {
    }

    public TipoCuenta(Integer idTipoCuenta) {
        IdTipoCuenta = idTipoCuenta;
    }


    // Getters y Setters

    public Integer getIdTipoCuenta() {
        return IdTipoCuenta;
    }

    public void setIdTipoCuenta(Integer idTipoCuenta) {
        IdTipoCuenta = idTipoCuenta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getRequisitosApertura() {
        return RequisitosApertura;
    }

    public void setRequisitosApertura(String requisitosApertura) {
        RequisitosApertura = requisitosApertura;
    }

    public String getTipocliente() {
        return tipocliente;
    }

    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    public String getCuentasContablesAsociadas() {
        return CuentasContablesAsociadas;
    }

    public void setCuentasContablesAsociadas(String cuentasContablesAsociadas) {
        CuentasContablesAsociadas = cuentasContablesAsociadas;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public Instant getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        FechaModificacion = fechaModificacion;
    }

    

    public Integer getIdTasaInteresPorDefecto() {
        return IdTasaInteresPorDefecto;
    }

    public void setIdTasaInteresPorDefecto(Integer idTasaInteresPorDefecto) {
        IdTasaInteresPorDefecto = idTasaInteresPorDefecto;
    }

    public TasaInteres getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(TasaInteres tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    // HashCode y Equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdTipoCuenta == null) ? 0 : IdTipoCuenta.hashCode());
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
        TipoCuenta other = (TipoCuenta) obj;
        if (IdTipoCuenta == null) {
            if (other.IdTipoCuenta != null)
                return false;
        } else if (!IdTipoCuenta.equals(other.IdTipoCuenta))
            return false;
        return true;
    }

    
    // ToString
    @Override
    public String toString() {
        return "TipoCuenta [IdTipoCuenta=" + IdTipoCuenta + ", IdTasaInteresPorDefecto=" + IdTasaInteresPorDefecto
                + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", RequisitosApertura=" + RequisitosApertura
                + ", tipocliente=" + tipocliente + ", CuentasContablesAsociadas=" + CuentasContablesAsociadas
                + ", Estado=" + Estado + ", FechaCreacion=" + FechaCreacion + ", FechaModificacion=" + FechaModificacion
                + ", tasaInteres=" + tasaInteres + "]";
    }


}
