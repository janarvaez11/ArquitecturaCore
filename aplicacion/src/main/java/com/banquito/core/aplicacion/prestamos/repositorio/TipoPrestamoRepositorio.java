package com.banquito.core.aplicacion.prestamos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.prestamos.modelo.TipoPrestamo;

public interface TipoPrestamoRepositorio extends JpaRepository<TipoPrestamo, Integer> {

}
