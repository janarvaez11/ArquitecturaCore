package com.banquito.core.aplicacion.prestamos.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ComisionesPrestamos")
public class ComisionPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdComisionPrestamo", nullable = false)
    private Integer id;

    @Column(name = "Nombre", length = 100)
    private String nombre;

    @Column(name = "TipoComision", length = 30)
    private String tipoComision;

    @Column(name = "TipoCalculo", length = 15)
    private String tipoCalculo;

    @Column(name = "Valor", precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "comisionPrestamo")
    private List<CondicionComisiones> condiciones;

    public ComisionPrestamo() {
    }

    public ComisionPrestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoComision() {
        return tipoComision;
    }

    public void setTipoComision(String tipoComision) {
        this.tipoComision = tipoComision;
    }

    public String getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<CondicionComisiones> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(List<CondicionComisiones> condiciones) {
        this.condiciones = condiciones;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        ComisionPrestamo other = (ComisionPrestamo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComisionesPrestamos [id=" + id + ", nombre=" + nombre + ", tipoComision=" + tipoComision
                + ", tipoCalculo=" + tipoCalculo + ", valor=" + valor + ", fechaCreacion=" + fechaCreacion + "]";
    }
} 