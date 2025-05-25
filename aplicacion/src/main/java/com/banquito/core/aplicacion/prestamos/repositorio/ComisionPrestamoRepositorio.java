package com.banquito.core.aplicacion.prestamos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;

public interface ComisionPrestamoRepositorio extends JpaRepository<ComisionPrestamo, Integer> {

}
