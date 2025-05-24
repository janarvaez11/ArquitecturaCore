package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.DireccionCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionClienteRepositorio extends JpaRepository<DireccionCliente, Integer> {
}
