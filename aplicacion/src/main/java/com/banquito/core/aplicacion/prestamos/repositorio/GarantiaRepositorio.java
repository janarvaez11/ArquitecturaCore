package com.banquito.core.aplicacion.prestamos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.prestamos.modelo.Garantia;

public interface GarantiaRepositorio extends JpaRepository<Garantia, Integer> {

}
