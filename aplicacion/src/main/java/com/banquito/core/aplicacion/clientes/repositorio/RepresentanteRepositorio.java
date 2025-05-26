package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Representante;
import com.banquito.core.aplicacion.clientes.modelo.RepresentanteId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepresentanteRepositorio extends JpaRepository<Representante, RepresentanteId> {

}
