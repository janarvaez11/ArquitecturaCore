package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.ComisionCargo;

public interface ComisionCargoRepositorio extends JpaRepository<ComisionCargo, Integer> {

}
