package com.banquito.core.aplicacion.cuentas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.EliminarEntidadExcepcion;
import com.banquito.core.aplicacion.cuentas.excepcion.TasaPlazoNoEncontradaExcepcion;
import com.banquito.core.aplicacion.cuentas.modelo.TasaPlazo;
import com.banquito.core.aplicacion.cuentas.repositorio.TasaPlazoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class TasaPlazoServicio {

    private final TasaPlazoRepositorio tasaPlazoRepositorio;

    public TasaPlazoServicio(TasaPlazoRepositorio tasaPlazoRepositorio) {
        this.tasaPlazoRepositorio = tasaPlazoRepositorio;
    }

    public List<TasaPlazo> findAll() {
        return this.tasaPlazoRepositorio.findAll();
    }

    public TasaPlazo findById(Integer id) {
        Optional<TasaPlazo> tasaPlazoOptional = this.tasaPlazoRepositorio.findById(id);
        if (tasaPlazoOptional.isPresent()) {
            return tasaPlazoOptional.get();
        } else {
            throw new TasaPlazoNoEncontradaExcepcion("TasaPlazo", "No se encontró la tasa por plazo con ID: " + id);
        }
    }

    @Transactional
    public void create(TasaPlazo tasaPlazo) {
        try {
            this.tasaPlazoRepositorio.save(tasaPlazo);
        } catch (Exception e) {
            throw new CrearEntidadExcepcion("TasaPlazo", "Error al crear la tasa por plazo. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void update(TasaPlazo tasaPlazo) {
        try {
            Optional<TasaPlazo> tasaPlazoOptional = this.tasaPlazoRepositorio.findById(tasaPlazo.getIdPlazo());
            if (tasaPlazoOptional.isPresent()) {
                TasaPlazo tasaPlazoDB = tasaPlazoOptional.get();
                tasaPlazoDB.setPlazoMinimo(tasaPlazo.getPlazoMinimo());
                tasaPlazoDB.setPlazoMaximo(tasaPlazo.getPlazoMaximo());
                tasaPlazoDB.setTasa(tasaPlazo.getTasa());
                this.tasaPlazoRepositorio.save(tasaPlazoDB);
            } else {
                throw new TasaPlazoNoEncontradaExcepcion("TasaPlazo", "No se encontró la tasa por plazo con ID: " + tasaPlazo.getIdPlazo());
            }
        } catch (Exception e) {
            throw new ActualizarEntidadExcepcion("TasaPlazo", "Error al actualizar la tasa por plazo. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            Optional<TasaPlazo> tasaPlazoOptional = this.tasaPlazoRepositorio.findById(id);
            if (tasaPlazoOptional.isPresent()) {
                this.tasaPlazoRepositorio.delete(tasaPlazoOptional.get());
            } else {
                throw new TasaPlazoNoEncontradaExcepcion("TasaPlazo", "No se encontró la tasa por plazo con ID: " + id);
            }
        } catch (Exception e) {
            throw new EliminarEntidadExcepcion("TasaPlazo", "Error al eliminar la tasa por plazo. Error: " + e.getMessage());
        }
    }
}
