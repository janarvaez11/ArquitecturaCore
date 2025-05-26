package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.Cuenta;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
    
    @Query("SELECT c FROM Cuenta c WHERE c.tipoCuenta.IdTipoCuenta = :idTipoCuenta")
    List<Cuenta> findByTipoCuentaId(@Param("idTipoCuenta") Integer idTipoCuenta);
    
    @Query("SELECT c FROM Cuenta c WHERE c.Estado = :estado")
    List<Cuenta> findByEstado(@Param("estado") String estado);
}