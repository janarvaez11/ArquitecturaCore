package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.repositorio.TelefonoClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TelefonoClienteServicio {

    private final TelefonoClienteRepositorio repositorio;

    public TelefonoClienteServicio(TelefonoClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<TelefonoCliente> buscarTodos() {
        return this.repositorio.findAll();
    }

    public TelefonoCliente buscarPorId(Integer id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new NoEncontradoExcepcion(id.toString(), "TelefonoCliente"));
    }
}
