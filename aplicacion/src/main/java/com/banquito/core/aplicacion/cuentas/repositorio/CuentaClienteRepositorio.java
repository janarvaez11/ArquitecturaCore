package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.CuentaCliente;

public interface CuentaClienteRepositorio extends JpaRepository<CuentaCliente, Integer> {

}