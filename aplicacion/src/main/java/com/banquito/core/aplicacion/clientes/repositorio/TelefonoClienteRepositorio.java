package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoClienteRepositorio extends JpaRepository<TelefonoCliente, Integer> {
    List<TelefonoCliente> findByCliente_idCliente(Integer idCliente);
}
