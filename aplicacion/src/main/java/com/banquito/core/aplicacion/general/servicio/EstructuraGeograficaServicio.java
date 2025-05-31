package com.banquito.core.aplicacion.general.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.repositorio.EstructuraGeograficaRepositorio;
import com.banquito.core.aplicacion.general.excepcion.EstructuraGeograficaNoEncontradaExcepcion;

@Service
public class EstructuraGeograficaServicio {

    private final EstructuraGeograficaRepositorio repositorio;

    public EstructuraGeograficaServicio(EstructuraGeograficaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<EstructuraGeografica> listarTodas() {
        List<EstructuraGeografica> estructuras = this.repositorio.findAll();
        if (estructuras.isEmpty()) {
            throw new EstructuraGeograficaNoEncontradaExcepcion(
                    "No hay estructuras geográficas registradas en el sistema.");
        }
        return estructuras;
    }

}