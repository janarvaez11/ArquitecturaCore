package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.TelefonoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TelefonoClienteRepositorio extends JpaRepository<TelefonoCliente, Integer> {
    List<TelefonoCliente> findByCliente_idCliente(Integer idCliente);
}
