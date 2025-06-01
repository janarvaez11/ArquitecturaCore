package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.repositorio.TelefonoClienteRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.TelefonoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearTelefonoExcepcion;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefonoClienteServicio {

    private final TelefonoClienteRepositorio telefonoRepo;
    private final ClienteRepositorio clienteRepo;

    public TelefonoClienteServicio(TelefonoClienteRepositorio telefonoRepo, ClienteRepositorio clienteRepo) {
        this.telefonoRepo = telefonoRepo;
        this.clienteRepo = clienteRepo;
    }

    public TelefonoCliente crear(Integer idCliente, TelefonoCliente telefono) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new CrearTelefonoExcepcion("Cliente no existe"));
        telefono.setCliente(cliente);
        return telefonoRepo.save(telefono);
    }

    public TelefonoCliente buscarPorId(Integer id) {
        return telefonoRepo.findById(id)
                .orElseThrow(() -> new TelefonoNoEncontradoExcepcion(id));
    }

    public TelefonoCliente actualizar(TelefonoCliente actualizado) {
        TelefonoCliente tel = buscarPorId(actualizado.getId());
        tel.setNumeroTelefono(actualizado.getNumeroTelefono());
        tel.setTipoTelefono(actualizado.getTipoTelefono());
        tel.setEstado(actualizado.getEstado());
        tel.setFechaActualizacion(actualizado.getFechaActualizacion());
        return telefonoRepo.save(tel);
    }

    public void eliminarLogico(Integer id) {
        TelefonoCliente tel = buscarPorId(id);
        tel.setEstado("INACTIVO");
        telefonoRepo.save(tel);
    }

    public List<TelefonoCliente> obtenerPorCliente(Integer idCliente) {
        return telefonoRepo.findByCliente_Id(idCliente);
    }
}
