package com.banquito.core.aplicacion.cuentas.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TasaSaldos")
public class TasaSaldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usa IDENTITY para campos SERIAL en PostgreSQL
    @Column(name = "IdSaldo", nullable = false)
    private Integer IdSaldo;

    
    @Column(name = "IdTasaInteres", nullable = false)
    private Integer IdTasaInteres;

    @Column(name = "Nombre", length = 20, nullable = false)
    private String Nombre;

    @Column(name = "SaldoMinimo", precision = 15, scale = 2)
    private BigDecimal SaldoMinimo;

    @Column(name = "SaldoMaximo", precision = 15, scale = 2)
    private BigDecimal SaldoMaximo;

    @Column(name = "Tasa", precision = 5, scale = 2)
    private BigDecimal Tasa;


    //Relacion a la tabla TasaInteres
    @ManyToOne
    @JoinColumn(name = "IdTasaInteres", referencedColumnName = "IdTasaInteres", nullable = false)
    private TasaInteres tasaInteres;


    // Constructores
    public TasaSaldo() {
    }
    public TasaSaldo(Integer idSaldo) {
        IdSaldo = idSaldo;
    }


    // Getters y Setters


    public Integer getIdSaldo() {
        return IdSaldo;
    }


    public void setIdSaldo(Integer idSaldo) {
        IdSaldo = idSaldo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public BigDecimal getSaldoMinimo() {
        return SaldoMinimo;
    }

    public void setSaldoMinimo(BigDecimal saldoMinimo) {
        SaldoMinimo = saldoMinimo;
    }

    public BigDecimal getSaldoMaximo() {
        return SaldoMaximo;
    }

    public void setSaldoMaximo(BigDecimal saldoMaximo) {
        SaldoMaximo = saldoMaximo;
    }

    public BigDecimal getTasa() {
        return Tasa;
    }

    public void setTasa(BigDecimal tasa) {
        Tasa = tasa;
    }



    public Integer getIdTasaInteres() {
        return IdTasaInteres;
    }
    public void setIdTasaInteres(Integer idTasaInteres) {
        IdTasaInteres = idTasaInteres;
    }
    public TasaInteres getTasaInteres() {
        return tasaInteres;
    }
    public void setTasaInteres(TasaInteres tasaInteres) {
        this.tasaInteres = tasaInteres;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdSaldo == null) ? 0 : IdSaldo.hashCode());
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
        TasaSaldo other = (TasaSaldo) obj;
        if (IdSaldo == null) {
            if (other.IdSaldo != null)
                return false;
        } else if (!IdSaldo.equals(other.IdSaldo))
            return false;
        return true;
    }

    //ToString
    @Override
    public String toString() {
        return "TasaSaldo [IdSaldo=" + IdSaldo + ", IdTasaInteres=" + IdTasaInteres + ", Nombre=" + Nombre
                + ", SaldoMinimo=" + SaldoMinimo + ", SaldoMaximo=" + SaldoMaximo + ", Tasa=" + Tasa + ", tasaInteres="
                + tasaInteres + "]";
    }

    

    

    
    




}
