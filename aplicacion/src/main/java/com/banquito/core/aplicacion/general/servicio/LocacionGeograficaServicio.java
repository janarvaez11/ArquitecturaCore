package com.banquito.core.aplicacion.general.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.core.aplicacion.general.modelo.LocacionGeografica;
import com.banquito.core.aplicacion.general.repositorio.LocacionGeograficaRepositorio;
import com.banquito.core.aplicacion.general.excepcion.LocacionGeograficaNoEncontradaExcepcion;

@Service
public class LocacionGeograficaServicio {

    private final LocacionGeograficaRepositorio repositorio;

    public LocacionGeograficaServicio(LocacionGeograficaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<LocacionGeografica> listarTodas() {
        List<LocacionGeografica> lista = this.repositorio.findAll();
        if (lista.isEmpty()) {
            throw new LocacionGeograficaNoEncontradaExcepcion("No hay locaciones registradas");
        }
        return lista;
    }

}