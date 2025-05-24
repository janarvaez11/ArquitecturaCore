package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {

}