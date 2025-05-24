package com.banquito.core.aplicacion.cuentas.modelo;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ComisionesCargos")

public class ComisionCargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComisionCargo", nullable = false)
    private Integer IdComisionCargo;

    @Column(name = "TipoComision", length = 30, nullable = false)
    private String TipoComision;

    @Column(name = "IdServicio", nullable = false)
    private Integer IdServicio;

    @Column(name = "Nombre", length = 30, nullable = false)
    private String Nombre;

    @Column(name = "BaseCalculo", length = 20, nullable = false)
    private String BaseCalculo;

    @Column(name = "Monto", precision = 15, scale = 2)
    private BigDecimal Monto;

    @Column(name = "Frecuencia", length = 30, nullable = false)
    private String Frecuencia;


    //Relacion a la tabla ServiciosAsociados
    @ManyToOne
    @JoinColumn(name = "IdServicio", referencedColumnName = "IdServicio")
    private ServicioAsociado servicioAsociado;


    //Relacion a la tabla ExencionCuentas y ComisionesCargos
    @OneToMany(mappedBy = "comision")
    private List<ExencionCuenta> exenciones;

    @OneToMany(mappedBy = "comisionCargo")
    private List<CuentaComisionCargo> cuentaComisionCargos;


    //Constructores
    public ComisionCargo() {
    }
    public ComisionCargo(Integer idComisionCargo) {
        IdComisionCargo = idComisionCargo;
    }

    //Getters y Setters
    public Integer getIdComisionCargo() {
        return IdComisionCargo;
    }
    public void setIdComisionCargo(Integer idComisionCargo) {
        IdComisionCargo = idComisionCargo;
    }
    public String getTipoComision() {
        return TipoComision;
    }
    public void setTipoComision(String tipoComision) {
        TipoComision = tipoComision;
    }
    public Integer getIdServicio() {
        return IdServicio;
    }
    public void setIdServicio(Integer idServicio) {
        IdServicio = idServicio;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getBaseCalculo() {
        return BaseCalculo;
    }
    public void setBaseCalculo(String baseCalculo) {
        BaseCalculo = baseCalculo;
    }
    public BigDecimal getMonto() {
        return Monto;
    }
    public void setMonto(BigDecimal monto) {
        Monto = monto;
    }
    public String getFrecuencia() {
        return Frecuencia;
    }
    public void setFrecuencia(String frecuencia) {
        Frecuencia = frecuencia;
    }
    public List<ExencionCuenta> getExenciones() {
        return exenciones;
    }
    public void setExenciones(List<ExencionCuenta> exenciones) {
        this.exenciones = exenciones;
    }
    public List<CuentaComisionCargo> getCuentaComisionCargos() {
        return cuentaComisionCargos;
    }
    public void setCuentaComisionCargos(List<CuentaComisionCargo> cuentaComisionCargos) {
        this.cuentaComisionCargos = cuentaComisionCargos;
    }

    //HashCode y Equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdComisionCargo == null) ? 0 : IdComisionCargo.hashCode());
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
        ComisionCargo other = (ComisionCargo) obj;
        if (IdComisionCargo == null) {
            if (other.IdComisionCargo != null)
                return false;
        } else if (!IdComisionCargo.equals(other.IdComisionCargo))
            return false;
        return true;
    }

    //ToString
    @Override
    public String toString() {
        return "ComisionCargo [IdComisionCargo=" + IdComisionCargo + ", TipoComision=" + TipoComision + ", IdServicio="
                + IdServicio + ", Nombre=" + Nombre + ", BaseCalculo=" + BaseCalculo + ", Monto=" + Monto
                + ", Frecuencia=" + Frecuencia + ", exenciones=" + exenciones + ", cuentaComisionCargos="
                + cuentaComisionCargos + "]";
    }

    


    

    

}
