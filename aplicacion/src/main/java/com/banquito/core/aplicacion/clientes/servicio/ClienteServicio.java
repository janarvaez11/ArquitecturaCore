package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteServicio(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public List<Cliente> buscarTodos() {
        return this.clienteRepositorio.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return this.clienteRepositorio.findById(id)
                .orElseThrow(() -> new NoEncontradoExcepcion(id.toString(), "Cliente"));
    }
}

