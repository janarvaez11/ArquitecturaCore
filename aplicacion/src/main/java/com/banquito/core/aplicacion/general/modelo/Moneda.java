package com.banquito.core.aplicacion.general.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Monedas")
public class Moneda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMoneda", nullable = false)
    private Integer id;

    @Column(name = "IdPais", length = 2, nullable = false)
    private String idPais;

    @Column(name = "IdEntidadBancaria", nullable = false)
    private Integer idEntidadBancaria;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "Simbolo", length = 5, nullable = false)
    private String simbolo;

    @Column(name = "codigo", length = 3, nullable = false)
    private String codigo;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "IdPais", referencedColumnName = "IdPais")
    private Pais pais;
}
