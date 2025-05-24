package com.banquito.core.aplicacion.clientes.controlador.mapper;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.dto.ClienteDTO;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setTipoEntidad(cliente.getTipoEntidad());
        dto.setIdEntidad(cliente.getIdEntidad());
        dto.setNacionalidad(cliente.getNacionalidad());
        dto.setIdSucursal(cliente.getIdSucursal());
        dto.setTipoIdentificacion(cliente.getTipoIdentificacion());
        dto.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        dto.setTipoCliente(cliente.getTipoCliente());
        dto.setSegmento(cliente.getSegmento());
        dto.setCanalAfilicacion(cliente.getCanalAfilicacion());
        dto.setNombre(cliente.getNombre());
        dto.setEstado(cliente.getEstado());
        return dto;
    }

    public static Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(dto.getIdCliente());
        cliente.setTipoEntidad(dto.getTipoEntidad());
        cliente.setIdEntidad(dto.getIdEntidad());
        cliente.setNacionalidad(dto.getNacionalidad());
        cliente.setIdSucursal(dto.getIdSucursal());
        cliente.setTipoIdentificacion(dto.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        cliente.setTipoCliente(dto.getTipoCliente());
        cliente.setSegmento(dto.getSegmento());
        cliente.setCanalAfilicacion(dto.getCanalAfilicacion());
        cliente.setNombre(dto.getNombre());
        cliente.setEstado(dto.getEstado());
        return cliente;
    }
}
