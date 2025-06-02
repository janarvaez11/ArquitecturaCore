package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.Retiro;

public interface RetiroRepositorio extends JpaRepository<Retiro, Integer> {

}
