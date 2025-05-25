package com.banquito.core.aplicacion.cuentas.modelo;

//import java.util.List;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Cuentas")

public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCuenta", nullable = false)
    private Integer IdCuenta;

    @Column(name = "IdTipoCuenta", nullable = false)
    private Integer IdTipoCuenta;

    @Column(name = "IdTasaInteres", nullable = false)
    private Integer IdTasaInteres;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String Nombre;

    @Column(name = "Descripcion", length = 50, nullable = false)
    private String Descripcion;

    @Column(name = "Estado", length = 10, nullable = false)
    private String Estado;

    @Temporal (TemporalType.TIMESTAMP)
    @Column(name = "FechaCreacion", nullable = false)
    private Date FechaCreacion;

    @Temporal (TemporalType.TIMESTAMP)
    @Column(name = "FechaModificacion", nullable = false)
    private Date FechaModificacion;

    // Relación con la tabla TasasInteres
    @ManyToOne
    @JoinColumn(name = "IdTasaInteres", referencedColumnName = "IdTasaInteres", nullable = false)
    private TasaInteres tasaInteres;

    // Relación con la tabla TipoCuenta
    @ManyToOne
    @JoinColumn(name = "IdTipoCuenta", referencedColumnName = "IdTipoCuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    // Relación inversa con CuentasClientes
    // @OneToMany(mappedBy = "cuenta")
    // private List<CuentaCliente> cuentasClientes;

    // Constructores
    public Cuenta() {
    }

    public Cuenta(Integer idCuenta) {
        IdCuenta = idCuenta;
    }

    // Getters y Setters
    public Integer getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        IdCuenta = idCuenta;
    }

    public Integer getIdTipoCuenta() {
        return IdTipoCuenta;
    }

    public void setIdTipoCuenta(Integer idTipoCuenta) {
        IdTipoCuenta = idTipoCuenta;
    }

    public Integer getIdTasaInteres() {
        return IdTasaInteres;
    }

    public void setIdTasaInteres(Integer idTasaInteres) {
        IdTasaInteres = idTasaInteres;
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

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        FechaModificacion = fechaModificacion;
    }

    public TasaInteres getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(TasaInteres tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }


    //Metodo hashCode y equals
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
        Cuenta other = (Cuenta) obj;
        if (IdCuenta == null) {
            if (other.IdCuenta != null)
                return false;
        } else if (!IdCuenta.equals(other.IdCuenta))
            return false;
        return true;
    }

    // Método toString
    @Override
    public String toString() {
        return "Cuenta [IdCuenta=" + IdCuenta + ", IdTipoCuenta=" + IdTipoCuenta + ", IdTasaInteres=" + IdTasaInteres
                + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", Estado=" + Estado + ", FechaCreacion="
                + FechaCreacion + ", FechaModificacion=" + FechaModificacion + ", tasaInteres=" + tasaInteres
                + ", tipoCuenta=" + tipoCuenta + "]";
    }

}
