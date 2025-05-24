package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoClienteRepositorio extends JpaRepository<TelefonoCliente, Integer> {
}
