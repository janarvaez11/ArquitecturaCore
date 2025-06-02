package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.repositorio.TelefonoClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.CrearTelefonoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarTelefonoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.TelefonoNoEncontradoExcepcion;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TelefonoClienteServicio {

    private final TelefonoClienteRepositorio telefonoRepositorio;

    public TelefonoClienteServicio(TelefonoClienteRepositorio telefonoRepositorio) {
        this.telefonoRepositorio = telefonoRepositorio;
    }

    public TelefonoCliente buscarPorId(Integer id) {
        return telefonoRepositorio.findById(id)
                .orElseThrow(() -> new TelefonoNoEncontradoExcepcion(id));
    }

    public List<TelefonoCliente> buscarTodos() {
        return telefonoRepositorio.findAll();
    }

    public TelefonoCliente crear(TelefonoCliente telefono) {
        try {
            telefono.setFechaCreacion(new Date());
            return telefonoRepositorio.save(telefono);
        } catch (Exception e) {
            throw new CrearTelefonoExcepcion("Error al crear teléfono");
        }
    }

    public TelefonoCliente modificar(TelefonoCliente telefono) {
        if (!telefonoRepositorio.existsById(telefono.getIdTelefonoCliente())) {
            throw new TelefonoNoEncontradoExcepcion(telefono.getIdTelefonoCliente());
        }
        try {
            telefono.setFechaActualizacion(new Date());
            return telefonoRepositorio.save(telefono);
        } catch (Exception e) {
            throw new ActualizarTelefonoExcepcion("Error al actualizar teléfono");
        }
    }

    @Transactional
    public void eliminarLogicamente(Integer id) {
        TelefonoCliente telefono = buscarPorId(id); // Ya tienes este método
        telefono.setEstado("INACTIVO");
        telefono.setFechaActualizacion(new Date());
        telefonoRepositorio.save(telefono);
    }

}
