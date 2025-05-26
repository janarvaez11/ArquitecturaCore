package com.banquito.core.aplicacion.cuentas.repositorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.CuentaCliente;

@Repository
public interface CuentaClienteRepositorio extends JpaRepository<CuentaCliente, Integer> {
    
    @Query("SELECT cc FROM CuentaCliente cc WHERE cc.cliente.idCliente = :idCliente")
    List<CuentaCliente> findByClienteId(@Param("idCliente") Integer idCliente);
    
    @Query("SELECT cc FROM CuentaCliente cc WHERE cc.Estado = :estado")
    List<CuentaCliente> findByEstado(@Param("estado") String estado);
    
    @Query("SELECT cc FROM CuentaCliente cc WHERE cc.SaldoDisponible > :saldoMinimo")
    List<CuentaCliente> findBySaldoDisponibleGreaterThan(@Param("saldoMinimo") BigDecimal saldoMinimo);
    
    @Query("SELECT cc FROM CuentaCliente cc WHERE cc.NumeroCuenta = :numeroCuenta")
    CuentaCliente findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);
}