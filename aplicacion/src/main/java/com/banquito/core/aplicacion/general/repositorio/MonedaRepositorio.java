package com.banquito.core.aplicacion.general.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.general.modelo.Moneda;


public interface MonedaRepositorio extends JpaRepository<Moneda, String> {

}
