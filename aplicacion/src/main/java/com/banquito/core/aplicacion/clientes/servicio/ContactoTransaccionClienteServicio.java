package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.repositorio.ContactoTransaccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.CrearContactoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ActualizarContactoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ContactoNoEncontradoExcepcion;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ContactoTransaccionClienteServicio {

    private final ContactoTransaccionClienteRepositorio contactoRepositorio;

    public ContactoTransaccionClienteServicio(ContactoTransaccionClienteRepositorio contactoRepositorio) {
        this.contactoRepositorio = contactoRepositorio;
    }

    public ContactoTransaccionCliente buscarPorId(Integer idCliente) {
        return contactoRepositorio.findById(idCliente)
                .orElseThrow(() -> new ContactoNoEncontradoExcepcion(idCliente));
    }

    public ContactoTransaccionCliente crear(ContactoTransaccionCliente contacto) {
        try {
            contacto.setFechaCreacion(new Date());
            return contactoRepositorio.save(contacto);
        } catch (Exception e) {
            throw new CrearContactoExcepcion("Error al crear contacto transaccional");
        }
    public ContactoTransaccionCliente obtenerPorCliente(Integer idCliente) {
        return contactoRepo.findByCliente_idCliente(idCliente)
                .orElseThrow(() -> new ContactoNoEncontradoExcepcion(idCliente));
    }

    public ContactoTransaccionCliente modificar(ContactoTransaccionCliente contacto) {
        if (!contactoRepositorio.existsById(contacto.getIdCliente())) {
            throw new ContactoNoEncontradoExcepcion(contacto.getIdCliente());
        }
        try {
            contacto.setFechaActualizacion(new Date());
            return contactoRepositorio.save(contacto);
        } catch (Exception e) {
            throw new ActualizarContactoExcepcion("Error al actualizar contacto");
        }
    }
}

