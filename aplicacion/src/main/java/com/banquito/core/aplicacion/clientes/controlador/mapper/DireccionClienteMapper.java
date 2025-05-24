package com.banquito.core.aplicacion.clientes.controlador.mapper;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import com.banquito.core.aplicacion.clientes.dto.DireccionClienteDTO;

public class DireccionClienteMapper {

    public static DireccionClienteDTO toDTO(DireccionCliente dir) {
        DireccionClienteDTO dto = new DireccionClienteDTO();
        dto.setIdDireccion(dir.getIdDireccion());
        dto.setIdCliente(dir.getCliente().getIdCliente());
        dto.setTipo(dir.getTipo());
        dto.setEstado(dir.getEstado());
        dto.setLinea1(dir.getLinea1());
        dto.setLinea2(dir.getLinea2());
        dto.setCodigoPostal(dir.getCodigoPostal());
        return dto;
    }

    public static DireccionCliente toEntity(DireccionClienteDTO dto, Cliente cliente) {
        DireccionCliente dir = new DireccionCliente();
        dir.setIdDireccion(dto.getIdDireccion());
        dir.setCliente(cliente);
        dir.setTipo(dto.getTipo());
        dir.setEstado(dto.getEstado());
        dir.setLinea1(dto.getLinea1());
        dir.setLinea2(dto.getLinea2());
        dir.setCodigoPostal(dto.getCodigoPostal());
        return dir;
    }
}

