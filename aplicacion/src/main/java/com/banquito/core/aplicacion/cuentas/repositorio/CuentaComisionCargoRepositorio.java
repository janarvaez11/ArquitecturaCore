package com.banquito.core.aplicacion.cuentas.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.CuentaComisionCargo;

public interface CuentaComisionCargoRepositorio extends JpaRepository<CuentaComisionCargo, Integer> {

}
