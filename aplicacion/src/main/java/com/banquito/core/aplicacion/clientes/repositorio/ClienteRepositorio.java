package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByEstado(String estado);
}

