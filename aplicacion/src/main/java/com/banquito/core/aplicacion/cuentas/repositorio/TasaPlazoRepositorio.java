package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.TasaPlazo;

public interface TasaPlazoRepositorio extends JpaRepository<TasaPlazo, Integer> {
    // Aquí puedes agregar métodos personalizados de búsqueda si los necesitas
}