package com.banquito.core.aplicacion.cuentas.modelo;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "CuentasComisionesCargos")

public class CuentaComisionCargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCuenta", nullable = false)
    private Integer IdCuenta;

    @Column(name = "IdComisionCargo", nullable = false)
    private Integer IdComisionCargo;

    @Temporal (TemporalType.TIMESTAMP)
    @Column(name = "FechaAsignacion", nullable = false)
    private Date FechaAsignacion;

    //Relacion a la tabla Cuentas y ComisionesCargos
    @ManyToOne
    @JoinColumn(name = "IdCuenta", insertable = false, updatable = false)
    private Cuenta cuenta;

    @ManyToOne
    @JoinColumn(name = "IdComisionCargo", insertable = false, updatable = false)
    private ComisionCargo comisionCargo;

    
    //Constructores
    public CuentaComisionCargo() {
    }

    public CuentaComisionCargo(Integer idCuenta) {
        IdCuenta = idCuenta;
    }

    //Getters y Setters

    public Integer getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        IdCuenta = idCuenta;
    }

    public Integer getIdComisionCargo() {
        return IdComisionCargo;
    }

    public void setIdComisionCargo(Integer idComisionCargo) {
        IdComisionCargo = idComisionCargo;
    }
    
    public Date getFechaAsignacion() {
        return FechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        FechaAsignacion = fechaAsignacion;
    }


    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public ComisionCargo getComisionCargo() {
        return comisionCargo;
    }

    public void setComisionCargo(ComisionCargo comisionCargo) {
        this.comisionCargo = comisionCargo;
    }

    //HashCode y Equals
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdCuenta == null) ? 0 : IdCuenta.hashCode());
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
        CuentaComisionCargo other = (CuentaComisionCargo) obj;
        if (IdCuenta == null) {
            if (other.IdCuenta != null)
                return false;
        } else if (!IdCuenta.equals(other.IdCuenta))
            return false;
        return true;
    }

    //ToString

    @Override
    public String toString() {
        return "CuentaComisionCargo [IdCuenta=" + IdCuenta + ", IdComisionCargo=" + IdComisionCargo
                + ", FechaAsignacion=" + FechaAsignacion + ", cuenta=" + cuenta + ", comisionCargo=" + comisionCargo
                + "]";
    }

}
