package com.banquito.core.aplicacion.prestamos.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.Version;

@Entity
@Table(name = "TiposPrestamos")
public class TipoPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTipoPrestamo", nullable = false)
    private Integer IdTipoPrestamo;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", length = 100, nullable = false)
    private String descripcion;

    @Column(name = "MontoMinito", precision = 15, scale = 2, nullable = false)
    private BigDecimal montoMinimo;

    @Column(name = "MondoMaximo", precision = 15, scale = 2, nullable = false)
    private BigDecimal montoMaximo;

    @Column(name = "PlazoMinimo", length = 2, nullable = false)
    private Integer plazoMinimo;

    @Column(name = "PlazoMaximo", length = 2, nullable = false)
    private Integer plazoMaximo;

    @Column(name = "Requisitos", length = 300, nullable = false)
    private String requisitos;

    @Column(name = "TipoCliente", length = 50, nullable = false)
    private String tipoCliente;

    @Column(name = "Estado", length = 10, nullable = false)
    private String estado;

    @Column(name = "FechaCreacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "FechaModifica", nullable = false)
    private LocalDate fechaModifica;

    @ManyToOne
    @JoinColumn(name = "IdTipoPrestamo", referencedColumnName = "IdTipoPrestamo")
    private Moneda moneda;

    @OneToMany(mappedBy = "tipoPrestamo")
    private List<Garantia> Garantias;

    @OneToMany(mappedBy = "tipoPrestamo")
    private List<Prestamo> Prestamos;

    // Constructor
    public TipoPrestamo() {
    }

    public TipoPrestamo(Integer idTipoPrestamo) {
        IdTipoPrestamo = idTipoPrestamo;
    }

    public Integer getIdTipoPrestamo() {
        return IdTipoPrestamo;
    }

    public void setIdTipoPrestamo(Integer idTipoPrestamo) {
        IdTipoPrestamo = idTipoPrestamo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(BigDecimal montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public Integer getPlazoMinimo() {
        return plazoMinimo;
    }

    public void setPlazoMinimo(Integer plazoMinimo) {
        this.plazoMinimo = plazoMinimo;
    }

    public Integer getPlazoMaximo() {
        return plazoMaximo;
    }

    public void setPlazoMaximo(Integer plazoMaximo) {
        this.plazoMaximo = plazoMaximo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModifica() {
        return fechaModifica;
    }

    public void setFechaModifica(LocalDate fechaModifica) {
        this.fechaModifica = fechaModifica;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public List<Garantia> getGarantias() {
        return Garantias;
    }

    public void setGarantias(List<Garantia> garantias) {
        this.Garantias = garantias;
    }

    public List<Prestamo> getPrestamos() {
        return Prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        Prestamos = prestamos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IdTipoPrestamo == null) ? 0 : IdTipoPrestamo.hashCode());
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
        TipoPrestamo other = (TipoPrestamo) obj;
        if (IdTipoPrestamo == null) {
            if (other.IdTipoPrestamo != null)
                return false;
        } else if (!IdTipoPrestamo.equals(other.IdTipoPrestamo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TipoPrestamo [IdTipoPrestamo=" + IdTipoPrestamo + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", montoMinimo=" + montoMinimo + ", montoMaximo=" + montoMaximo + ", plazoMinimo=" + plazoMinimo
                + ", plazoMaximo=" + plazoMaximo + ", requisitos=" + requisitos + ", tipoCliente=" + tipoCliente
                + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion + ", fechaModifica=" + fechaModifica
                + ", moneda=" + moneda + "]";
    }

}
