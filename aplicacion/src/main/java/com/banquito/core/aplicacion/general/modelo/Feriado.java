package com.banquito.core.aplicacion.general.modelo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Feriados")
public class Feriado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFeriado", nullable = false)
    private Integer id;

    @Column(name = "FechaFeriado", nullable = false)
    private Date fechaFeriado;

    @ManyToOne
    @JoinColumn(name = "IdPais", referencedColumnName = "IdPais",nullable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "IdLocacion", referencedColumnName = "IdLocacion", nullable = false)
    private LocacionGeografica locacion;

    @Column(name = "Nombre", length = 25, nullable = false)
    private String nombre;

    @Column(name = "TipoFeriado", length = 3, nullable = false)
    private String tipoFeriado;

    @Version
    @Column(name = "Version", precision = 9, nullable = false)
    private Long version;
}
