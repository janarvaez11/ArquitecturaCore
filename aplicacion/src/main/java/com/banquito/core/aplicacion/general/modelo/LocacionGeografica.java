package com.banquito.core.aplicacion.general.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "LocacionGeografica")
public class LocacionGeografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLocacion")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "IdLocacionPadreId", referencedColumnName = "IdLocacion")
    private LocacionGeografica locacionPadre;

    @ManyToOne
    @JoinColumn(name = "IdPais", referencedColumnName = "IdPais")
    private Pais pais;

    @Column(name = "CodigoNivel")
    private Integer codigoNivel;

    @Column(name = "Nombre", length = 25, nullable = false)
    private String nombre;

    @Column(name = "CodigoTelefonoArea", length = 3)
    private String codigoTelefonoArea;

    @Column(name = "CodigoPostal", length = 10)
    private String codigoPostal;

    @OneToMany(mappedBy = "locacionPadre")
    private List<LocacionGeografica> locacionesHijas;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "IdPais", referencedColumnName = "IdPais", insertable = false, updatable = false),
        @JoinColumn(name = "CodigoNivel", referencedColumnName = "CodigoNivel", insertable = false, updatable = false)
    })
    private EstructuraGeografica estructuraGeografica;

    public LocacionGeografica() {
    }


}