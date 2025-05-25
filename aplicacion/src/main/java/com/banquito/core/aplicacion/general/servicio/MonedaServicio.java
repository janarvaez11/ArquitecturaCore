package com.banquito.core.aplicacion.general.servicio;

import com.banquito.core.aplicacion.general.modelo.EntidadBancaria;
import com.banquito.core.aplicacion.general.modelo.Moneda;
import com.banquito.core.aplicacion.general.repositorio.MonedaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MonedaServicio {
    //Monedas
    //1. Creación de monedas por pais
    //2. Asignación de moneda a una entidad bancaria

    private final MonedaRepositorio repositorio;

    public MonedaServicio(MonedaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Moneda findById(String id){
        Optional<Moneda> monedaOptional = repositorio.findById(id);
        if (monedaOptional.isPresent()){
            return monedaOptional.get();
        } else {
            throw new RuntimeException("El id: " + id + " no corresponde a ninguna moneda");
        }
    }

    public Moneda findDefault(){
        List<Moneda> list = this.repositorio.findAll();
        if (!list.isEmpty()){
            return list.getFirst();
        }else{
            throw new RuntimeException("No existen monedas registradas");
        }
    }

    @Transactional
    public void create(Moneda moneda) {
        try {
            this.repositorio.save(moneda);
        } catch (RuntimeException rte) {
            throw new RuntimeException("Error al crear la moneda. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void crearMonedaPorPais(Moneda moneda, Pais pais) {
        try {
            moneda.setPais(pais);
            this.repositorio.save(moneda);
        } catch (RuntimeException rte) {
            throw new RuntimeException("Error al crear la moneda por pais. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void asignarMonedaAEntidadBancaria(String idMoneda, EntidadBancaria entidadBancaria) {
        try {
            Optional<Moneda> monedaOptional = this.repositorio.findById(idMoneda);
            if (monedaOptional.isPresent()) {
                Moneda moneda = monedaOptional.get();
                moneda.setEntidadBancaria(entidadBancaria);
                this.repositorio.save(moneda);
            } else {
                throw new RuntimeException("La moneda con ID: " + idMoneda + " no existe");
            }
        } catch (RuntimeException rte) {
            throw new RuntimeException("Error al asignar la moneda a la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }
}
