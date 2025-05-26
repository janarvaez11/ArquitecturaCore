package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Accionista;
import com.banquito.core.aplicacion.clientes.modelo.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccionistaRepositorio extends JpaRepository<Accionista, Integer> {

}
