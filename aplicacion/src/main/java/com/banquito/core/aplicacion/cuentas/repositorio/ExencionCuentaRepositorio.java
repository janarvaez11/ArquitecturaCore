package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.ExencionCuenta;

public interface ExencionCuentaRepositorio extends JpaRepository<ExencionCuenta, Integer> {

}
