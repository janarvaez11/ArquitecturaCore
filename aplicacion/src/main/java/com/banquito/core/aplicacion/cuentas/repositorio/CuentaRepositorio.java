package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.Cuenta;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
    
    @Query("SELECT c FROM Cuenta c WHERE c.tipoCuenta.idTipoCuenta = :idTipoCuenta")
    List<Cuenta> findByTipoCuentaId(Integer idTipoCuenta);
    
    List<Cuenta> findByEstado(String estado);
}