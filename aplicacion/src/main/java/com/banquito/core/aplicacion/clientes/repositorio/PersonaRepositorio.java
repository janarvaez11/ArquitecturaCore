package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepositorio extends JpaRepository<Persona, Integer> {

    Optional<Persona> findByNumeroIdentificacion(String numeroIdentificacion);

}
