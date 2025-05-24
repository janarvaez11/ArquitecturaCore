package com.banquito.core.aplicacion.clientes.controlador.mapper;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import com.banquito.core.aplicacion.clientes.dto.TelefonoClienteDTO;

public class TelefonoClienteMapper {

    public static TelefonoClienteDTO toDTO(TelefonoCliente tel) {
        TelefonoClienteDTO dto = new TelefonoClienteDTO();
        dto.setIdTelefonoCliente(tel.getIdTelefonoCliente());
        dto.setIdCliente(tel.getCliente().getIdCliente());
        dto.setTipoTelefono(tel.getTipoTelefono());
        dto.setNumeroTelefono(tel.getNumeroTelefono());
        dto.setEstado(tel.getEstado());
        return dto;
    }

    public static TelefonoCliente toEntity(TelefonoClienteDTO dto, Cliente cliente) {
        TelefonoCliente tel = new TelefonoCliente();
        tel.setIdTelefonoCliente(dto.getIdTelefonoCliente());
        tel.setCliente(cliente);
        tel.setTipoTelefono(dto.getTipoTelefono());
        tel.setNumeroTelefono(dto.getNumeroTelefono());
        tel.setEstado(dto.getEstado());
        return tel;
    }
}
