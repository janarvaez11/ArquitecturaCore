package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Accionista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccionistaRepositorio extends JpaRepository<Accionista, Integer> {
}
