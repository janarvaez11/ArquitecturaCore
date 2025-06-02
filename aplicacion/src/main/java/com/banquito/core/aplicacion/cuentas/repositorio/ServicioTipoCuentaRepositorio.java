package com.banquito.core.aplicacion.cuentas.repositorio;

import com.banquito.core.aplicacion.cuentas.modelo.ServicioTipoCuenta;
import com.banquito.core.aplicacion.cuentas.modelo.ServicioTipoCuentaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioTipoCuentaRepositorio extends JpaRepository<ServicioTipoCuenta, ServicioTipoCuentaId> {
    List<ServicioTipoCuenta> findByCuenta_IdCuenta(Integer idCuenta);
}