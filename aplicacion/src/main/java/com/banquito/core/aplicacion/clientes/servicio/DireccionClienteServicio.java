package com.banquito.core.aplicacion.clientes.servicio;


import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.repositorio.DireccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DireccionClienteServicio {

    private final DireccionClienteRepositorio repositorio;

    public DireccionClienteServicio(DireccionClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<DireccionCliente> buscarTodos() {
        return this.repositorio.findAll();
    }

    public DireccionCliente buscarPorId(Integer id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new NoEncontradoExcepcion(id.toString(), "DireccionCliente"));
    }
}
