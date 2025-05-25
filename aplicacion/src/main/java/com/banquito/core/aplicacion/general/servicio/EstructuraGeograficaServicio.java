package com.banquito.core.aplicacion.general.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.modelo.EstructuraGeograficaId;
import com.banquito.core.aplicacion.general.repositorio.EstructuraGeograficaRepositorio;
import com.banquito.core.aplicacion.general.excepcion.CrearEstructuraGeograficaExcepcion;
import com.banquito.core.aplicacion.general.excepcion.ActualizarEstructuraGeograficaExcepcion;
import com.banquito.core.aplicacion.general.excepcion.EstructuraGeograficaNoEncontradaExcepcion;

@Service
public class EstructuraGeograficaServicio {

    private final EstructuraGeograficaRepositorio repositorio;

    public EstructuraGeograficaServicio(EstructuraGeograficaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public void crear(EstructuraGeografica estructura) {
        try {
            if (this.repositorio.existsById(estructura.getId())) {
                throw new CrearEstructuraGeograficaExcepcion("EstructuraGeografica",
                        "La estructura geográfica con ID " + estructura.getId() + " ya existe.");
            }
            this.repositorio.save(estructura);
        } catch (RuntimeException e) {
            throw new CrearEstructuraGeograficaExcepcion("EstructuraGeografica",
                    "Error al crear la estructura geográfica: " + e.getMessage());
        }
    }

    @Transactional
    public void actualizar(EstructuraGeografica estructura) {
        try {
            Optional<EstructuraGeografica> optional = this.repositorio.findById(estructura.getId());
            if (optional.isPresent()) {
                EstructuraGeografica db = optional.get();
                db.setNombre(estructura.getNombre());
                this.repositorio.save(db);
            } else {
                throw new EstructuraGeograficaNoEncontradaExcepcion(
                        "No se encontró la estructura geográfica con ID: " + estructura.getId());
            }
        } catch (RuntimeException e) {
            throw new ActualizarEstructuraGeograficaExcepcion("EstructuraGeografica",
                    "Error al actualizar la estructura geográfica: " + e.getMessage());
        }
    }

    public EstructuraGeografica obtenerPorId(EstructuraGeograficaId id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new EstructuraGeograficaNoEncontradaExcepcion(
                        "Estructura geográfica no encontrada con ID: " + id));
    }

    public List<EstructuraGeografica> listarTodas() {
        List<EstructuraGeografica> estructuras = this.repositorio.findAll();
        if (estructuras.isEmpty()) {
            throw new EstructuraGeograficaNoEncontradaExcepcion(
                    "No hay estructuras geográficas registradas en el sistema.");
        }
        return estructuras;
    }

    public List<EstructuraGeografica> buscarPorPais(String paisId) {
        List<EstructuraGeografica> estructuras = this.repositorio.findByPaisId(paisId);
        if (estructuras.isEmpty()) {
            throw new EstructuraGeograficaNoEncontradaExcepcion(
                    "No hay estructuras geográficas asociadas al país con ID: " + paisId);
        }
        return estructuras;
    }

    public List<EstructuraGeografica> buscarPorNivel(Integer codigoNivel) {
        List<EstructuraGeografica> estructuras = this.repositorio.findByIdCodigoNivel(codigoNivel);
        if (estructuras.isEmpty()) {
            throw new EstructuraGeograficaNoEncontradaExcepcion(
                    "No hay estructuras geográficas del nivel " + codigoNivel);
        }
        return estructuras;
    }

    public boolean existe(EstructuraGeograficaId id) {
        return this.repositorio.existsById(id);
    }
}