package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.repositorio.ContactoTransaccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.NoEncontradoExcepcion;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ContactoTransaccionClienteServicio {

    private final ContactoTransaccionClienteRepositorio repositorio;

    public ContactoTransaccionClienteServicio(ContactoTransaccionClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<ContactoTransaccionCliente> buscarTodos() {
        return this.repositorio.findAll();
    }

    public ContactoTransaccionCliente buscarPorId(Integer id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new NoEncontradoExcepcion(id.toString(), "ContactoTransaccionCliente"));
    }
}

