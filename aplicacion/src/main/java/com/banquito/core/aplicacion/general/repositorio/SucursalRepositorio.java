package com.banquito.core.aplicacion.general.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.general.modelo.Sucursal;

public interface SucursalRepositorio extends JpaRepository<Sucursal, String> {

    List<Sucursal> findByEstado(String estado);

    List<Sucursal> findByLocacionId(Integer idLocacion);
    
}