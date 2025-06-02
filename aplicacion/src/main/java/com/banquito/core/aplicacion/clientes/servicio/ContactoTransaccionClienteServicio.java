package com.banquito.core.aplicacion.clientes.servicio;

import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.repositorio.ContactoTransaccionClienteRepositorio;
import com.banquito.core.aplicacion.clientes.excepcion.CrearContactoExcepcion;
import com.banquito.core.aplicacion.clientes.excepcion.ContactoNoEncontradoExcepcion;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ContactoTransaccionClienteServicio {

    private final ContactoTransaccionClienteRepositorio contactoRepo;

    public ContactoTransaccionClienteServicio(ContactoTransaccionClienteRepositorio contactoRepo) {
        this.contactoRepo = contactoRepo;
    }

    public ContactoTransaccionCliente buscarPorId(Integer idCliente) {
        return contactoRepo.findById(idCliente)
                .orElseThrow(() -> new ContactoNoEncontradoExcepcion(idCliente));
    }

    public ContactoTransaccionCliente crear(ContactoTransaccionCliente contacto) {
        try {
            contacto.setFechaCreacion(new Date());
            return contactoRepo.save(contacto);
        } catch (Exception e) {
            throw new CrearContactoExcepcion("Error al crear contacto transaccional");
        }
    }

    public ContactoTransaccionCliente obtenerPorCliente(Integer idCliente) {
        return contactoRepo.findByCliente_idCliente(idCliente)
                .orElseThrow(() -> new ContactoNoEncontradoExcepcion(idCliente));
    }

}
