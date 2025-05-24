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

    @Column(name = "IdPais", length = 2, nullable = false)
    private String idPais;

    @Column(name = "IdLocacion", nullable = false)
    private Integer idLocacion;

    @Column(name = "Nombre", length = 25, nullable = false)
    private String nombre;

    @Column(name = "TipoFeriado", length = 3, nullable = false)
    private String tipoFeriado;

    @Version
    private Long version;
}
