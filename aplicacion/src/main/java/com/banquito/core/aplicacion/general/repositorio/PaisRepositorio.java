package com.banquito.core.aplicacion.general.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.general.modelo.Pais;


public interface PaisRepositorio extends JpaRepository<Pais, String> {

}
