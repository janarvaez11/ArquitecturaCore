package com.banquito.core.aplicacion.general.servicio;

import com.banquito.core.aplicacion.general.excepcion.ActualizarEntidadException;
import com.banquito.core.aplicacion.general.excepcion.CrearEntidadException;
import com.banquito.core.aplicacion.general.excepcion.MonedaNoEncontradaException;
import com.banquito.core.aplicacion.general.modelo.EntidadBancaria;
import com.banquito.core.aplicacion.general.modelo.Moneda;
import com.banquito.core.aplicacion.general.repositorio.MonedaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MonedaServicio {

    private final MonedaRepositorio repositorio;
    private final EntidadBancariaServicio entidadBancariaServicio;

    public MonedaServicio(MonedaRepositorio repositorio, EntidadBancariaServicio entidadBancariaServicio) {
        this.repositorio = repositorio;
        this.entidadBancariaServicio = entidadBancariaServicio;
    }

    public Moneda findById(String id){
        Optional<Moneda> monedaOptional = repositorio.findById(id);
        if (monedaOptional.isPresent()){
            return monedaOptional.get();
        } else {
            throw new MonedaNoEncontradaException("El id: " + id + " no corresponde a ninguna moneda");
        }
    }

    public Moneda findDefault(){
        List<Moneda> list = this.repositorio.findAll();
        if (!list.isEmpty()){
            return list.getFirst();
        }else{
            throw new MonedaNoEncontradaException("No existen monedas registradas");
        }
    }

    @Transactional
    public void create(Moneda moneda) {
        try {
            this.repositorio.save(moneda);
        } catch (RuntimeException rte) {
            throw new CrearEntidadException("Moneda", "Error al crear la moneda. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void crearMonedaPorPais(Moneda moneda, Pais pais) {
        try {
            moneda.setPais(pais);
            this.repositorio.save(moneda);
        } catch (RuntimeException rte) {
            throw new CrearEntidadException("Moneda", "Error al crear la moneda por pais. Texto del error: " + rte.getMessage());
        }
    }

    @Transactional
    public void asignarMonedaAEntidadBancaria(String idMoneda, Integer idEntidadBancaria) {
        try {
            Optional<Moneda> monedaOptional = this.repositorio.findById(idMoneda);
            if (monedaOptional.isPresent()) {
                Moneda moneda = monedaOptional.get();
                EntidadBancaria entidadBancaria = entidadBancariaServicio.findById(idEntidadBancaria);
                moneda.setEntidadBancaria(entidadBancaria);
            } else {
                throw new MonedaNoEncontradaException("La moneda con ID: " + idMoneda + " no existe");
            }
        } catch (RuntimeException rte) {
            throw new ActualizarEntidadException("Moneda", "Error al asignar la moneda a la entidad bancaria. Texto del error: " + rte.getMessage());
        }
    }
}
