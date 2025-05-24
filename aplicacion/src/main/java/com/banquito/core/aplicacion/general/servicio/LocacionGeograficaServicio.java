package com.banquito.core.aplicacion.general.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.general.modelo.LocacionGeografica;
import com.banquito.core.aplicacion.general.repositorio.LocacionGeograficaRepositorio;
import com.banquito.core.aplicacion.general.excepcion.CrearLocacionGeograficaExcepcion;
import com.banquito.core.aplicacion.general.excepcion.ActualizarLocacionGeograficaExcepcion;
import com.banquito.core.aplicacion.general.excepcion.LocacionGeograficaNoEncontradaExcepcion;

@Service
public class LocacionGeograficaServicio {

    private final LocacionGeograficaRepositorio repositorio;

    public LocacionGeograficaServicio(LocacionGeograficaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public void crearLocacion(LocacionGeografica locacion) {
        try {
            if (this.repositorio.existsById(locacion.getId())) {
                throw new CrearLocacionGeograficaExcepcion("LocacionGeografica",
                        "Ya existe una locación con ID: " + locacion.getId());
            }
            this.repositorio.save(locacion);
        } catch (RuntimeException e) {
            throw new CrearLocacionGeograficaExcepcion("LocacionGeografica",
                    "Error al crear la locación geográfica: " + e.getMessage());
        }
    }

    @Transactional
    public void actualizarLocacion(LocacionGeografica locacion) {
        try {
            Optional<LocacionGeografica> optional = this.repositorio.findById(locacion.getId());
            if (optional.isPresent()) {
                LocacionGeografica existente = optional.get();
                existente.setNombre(locacion.getNombre());
                existente.setEstructuraGeografica(locacion.getEstructuraGeografica());
                //existente.setEstado(locacion.getEstado());
                this.repositorio.save(existente);
            } else {
                throw new LocacionGeograficaNoEncontradaExcepcion(
                        "No se encontró la locación con ID: " + locacion.getId());
            }
        } catch (RuntimeException e) {
            throw new ActualizarLocacionGeograficaExcepcion("LocacionGeografica",
                    "Error al actualizar la locación: " + e.getMessage());
        }
    }

    public LocacionGeografica obtenerPorId(Integer id) {
        return this.repositorio.findById(id).orElseThrow(() -> new LocacionGeograficaNoEncontradaExcepcion(
                "Locación no encontrada con ID: " + id));
    }

    public List<LocacionGeografica> listarTodas() {
        List<LocacionGeografica> lista = this.repositorio.findAll();
        if (lista.isEmpty()) {
            throw new LocacionGeograficaNoEncontradaExcepcion("No hay locaciones registradas");
        }
        return lista;
    }

    public List<LocacionGeografica> listarActivas() {
        List<LocacionGeografica> lista = this.repositorio.findByEstado("ACT");
        if (lista.isEmpty()) {
            throw new LocacionGeograficaNoEncontradaExcepcion("No hay locaciones activas");
        }
        return lista;
    }

    public List<LocacionGeografica> listarPorPais(String idPais) {
        List<LocacionGeografica> lista = this.repositorio.findByEstructuraGeograficaIdPaisId(idPais);
        if (lista.isEmpty()) {
            throw new LocacionGeograficaNoEncontradaExcepcion(
                    "No hay locaciones asociadas al país con ID: " + idPais);
        }
        return lista;
    }

    public List<LocacionGeografica> listarPorNivel(Integer nivel) {
        List<LocacionGeografica> lista = this.repositorio.findByEstructuraGeograficaIdCodigoNivel(nivel);
        if (lista.isEmpty()) {
            throw new LocacionGeograficaNoEncontradaExcepcion(
                    "No hay locaciones para el nivel geográfico: " + nivel);
        }
        return lista;
    }

    public boolean existeLocacion(Integer id) {
        return this.repositorio.existsById(id);
    }
}