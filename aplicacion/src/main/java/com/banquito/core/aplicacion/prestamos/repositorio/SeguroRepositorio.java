package com.banquito.core.aplicacion.prestamos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.prestamos.modelo.Seguro;

public interface SeguroRepositorio extends JpaRepository<Seguro, Integer> {

}
