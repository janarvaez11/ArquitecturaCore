package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;

@Repository
public interface TipoCuentaRepositorio extends JpaRepository<TipoCuenta, Integer> {
    
    List<TipoCuenta> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT t FROM TipoCuenta t WHERE t.idMoneda = :idMoneda")
    List<TipoCuenta> findByIdMoneda(String idMoneda);
    
    @Query("SELECT t FROM TipoCuenta t WHERE t.tipoCliente = :tipoCliente")
    List<TipoCuenta> findByTipoCliente(String tipoCliente);
}


