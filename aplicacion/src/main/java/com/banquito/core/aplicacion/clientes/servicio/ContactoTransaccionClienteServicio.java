package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.repositorio.ContactoTransaccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.repositorio.ClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.ContactoNoEncontradoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.CrearContactoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarContactoExcepcion;

import org.springframework.stereotype.Service;

@Service
public class ContactoTransaccionClienteServicio {

    private final ContactoTransaccionClienteRepositorio contactoRepo;
    private final ClienteRepositorio clienteRepo;

    public ContactoTransaccionClienteServicio(ContactoTransaccionClienteRepositorio contactoRepo, ClienteRepositorio clienteRepo) {
        this.contactoRepo = contactoRepo;
        this.clienteRepo = clienteRepo;
    }

    public ContactoTransaccionCliente crear(Integer idCliente, ContactoTransaccionCliente contacto) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new CrearContactoExcepcion("Cliente no existe"));
        contacto.setCliente(cliente);
        return contactoRepo.save(contacto);
    }

    public ContactoTransaccionCliente obtenerPorCliente(Integer idCliente) {
        return contactoRepo.findByCliente_idCliente(idCliente)
                .orElseThrow(() -> new ContactoNoEncontradoExcepcion(idCliente));
    }

    public ContactoTransaccionCliente actualizar(ContactoTransaccionCliente actualizado) {
        ContactoTransaccionCliente contacto = contactoRepo.findById(actualizado.getId())
                .orElseThrow(() -> new ActualizarContactoExcepcion("No se encontró contacto para actualizar"));
        contacto.setTelefono(actualizado.getTelefono());
        contacto.setCorreoElectronico(actualizado.getCorreoElectronico());
        contacto.setFechaActualizacion(actualizado.getFechaActualizacion());
        return contactoRepo.save(contacto);
    }
}
