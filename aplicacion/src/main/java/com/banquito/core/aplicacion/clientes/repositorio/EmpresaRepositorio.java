package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findByNumeroIdentificacion(String numeroIdentificacion);

}
