package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.CuentaCliente;

@Repository
public interface CuentaClienteRepositorio extends JpaRepository<CuentaCliente, Integer> {
    
    @Query("SELECT cc FROM CuentaCliente cc WHERE cc.cliente.idCliente = :idCliente")
    List<CuentaCliente> findByClienteId(Integer idCliente);
    
    List<CuentaCliente> findByEstado(String estado);
    
    List<CuentaCliente> findBySaldoDisponibleGreaterThan(Double saldoMinimo);
    
    CuentaCliente findByNumeroCuenta(String numeroCuenta);
}