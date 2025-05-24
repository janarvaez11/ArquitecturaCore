package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;

public interface CuentaClienteRepositorio extends JpaRepository<TipoCuenta, Integer> {

}
