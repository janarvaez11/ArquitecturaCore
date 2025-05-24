package com.banquito.core.aplicacion.cuentas.modelo;

import java.math.BigDecimal;
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
@Table(name = "CuentasClientes")

public class CuentaCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCuentaCliente", nullable = false)
    private Integer IdCuentaCliente;

    @Column(name = "IdCliente", nullable = false)
    private Integer IdCliente;

    @Column(name = "IdCuenta", nullable = false)
    private Integer IdCuenta;

    @Column(name = "NumeroCuenta", length = 10, nullable = false)
    private String NumeroCuenta;

    @Column(name = "Estado", length = 20, nullable = false)
    private String Estado;

    @Column(name = "SaldoDisponible", precision = 100, scale = 2)
    private BigDecimal SaldoDisponible;

    @Column(name = "SaldoContable", precision = 100, scale = 2)
    private BigDecimal SaldoContable;

    @Column(name = "FechaApertura")
    private Instant FechaApertura;


//Relacion a la tabla Cuentas
@ManyToOne
@JoinColumn(name = "IdCuenta", referencedColumnName = "IdCuenta", nullable = false)
private Cuenta cuenta;

//Constructores

public CuentaCliente() {
    }

public CuentaCliente(Integer idCuentaCliente) {
    IdCuentaCliente = idCuentaCliente;
}

//Getters y Setters

public Integer getIdCuentaCliente() {
    return IdCuentaCliente;
}

public void setIdCuentaCliente(Integer idCuentaCliente) {
    IdCuentaCliente = idCuentaCliente;
}

public Integer getIdCliente() {
    return IdCliente;
}

public void setIdCliente(Integer idCliente) {
    IdCliente = idCliente;
}

public Integer getIdCuenta() {
    return IdCuenta;
}

public void setIdCuenta(Integer idCuenta) {
    IdCuenta = idCuenta;
}

public String getNumeroCuenta() {
    return NumeroCuenta;
}

public void setNumeroCuenta(String numeroCuenta) {
    NumeroCuenta = numeroCuenta;
}

public String getEstado() {
    return Estado;
}

public void setEstado(String estado) {
    Estado = estado;
}

public BigDecimal getSaldoDisponible() {
    return SaldoDisponible;
}

public void setSaldoDisponible(BigDecimal saldoDisponible) {
    SaldoDisponible = saldoDisponible;
}

public BigDecimal getSaldoContable() {
    return SaldoContable;
}

public void setSaldoContable(BigDecimal saldoContable) {
    SaldoContable = saldoContable;
}

public Instant getFechaApertura() {
    return FechaApertura;
}

public void setFechaApertura(Instant fechaApertura) {
    FechaApertura = fechaApertura;
}

public Cuenta getCuenta() {
    return cuenta;
}

public void setCuenta(Cuenta cuenta) {
    this.cuenta = cuenta;
}

//HashCode y Equals
@Override
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((IdCuentaCliente == null) ? 0 : IdCuentaCliente.hashCode());
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
    CuentaCliente other = (CuentaCliente) obj;
    if (IdCuentaCliente == null) {
        if (other.IdCuentaCliente != null)
            return false;
    } else if (!IdCuentaCliente.equals(other.IdCuentaCliente))
        return false;
    return true;
}

//ToString
@Override
public String toString() {
    return "CuentaCliente [IdCuentaCliente=" + IdCuentaCliente + ", IdCliente=" + IdCliente + ", IdCuenta=" + IdCuenta
            + ", NumeroCuenta=" + NumeroCuenta + ", Estado=" + Estado + ", SaldoDisponible=" + SaldoDisponible
            + ", SaldoContable=" + SaldoContable + ", FechaApertura=" + FechaApertura + ", cuenta=" + cuenta + "]";
}
}