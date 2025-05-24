package com.banquito.core.aplicacion.clientes.controlador.mapper;

import com.banquito.core.aplicacion.clientes.modelo.ContactoTransaccionCliente;
import com.banquito.core.aplicacion.clientes.dto.ContactoTransaccionClienteDTO;

public class ContactoTransaccionClienteMapper {

    public static ContactoTransaccionClienteDTO toDTO(ContactoTransaccionCliente contacto) {
        ContactoTransaccionClienteDTO dto = new ContactoTransaccionClienteDTO();
        dto.setIdCliente(contacto.getIdCliente());
        dto.setTelefono(contacto.getTelefono());
        dto.setCorreoElectronico(contacto.getCorreoElectronico());
        return dto;
    }
}

