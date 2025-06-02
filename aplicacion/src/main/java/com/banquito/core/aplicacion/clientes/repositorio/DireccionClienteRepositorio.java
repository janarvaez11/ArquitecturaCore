package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionClienteRepositorio extends JpaRepository<DireccionCliente, Integer> {
    List<DireccionCliente> findByCliente_IdCliente(Integer idCliente);

}
