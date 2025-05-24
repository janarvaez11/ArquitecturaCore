package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.TasaInteres;

public interface TipoInteresRepositorio extends JpaRepository<TasaInteres, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
