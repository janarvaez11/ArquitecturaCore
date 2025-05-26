package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.TipoCuenta;

@Repository
public interface TipoCuentaRepositorio extends JpaRepository<TipoCuenta, Integer> {
    
    @Query("SELECT t FROM TipoCuenta t WHERE LOWER(t.Nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<TipoCuenta> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT t FROM TipoCuenta t WHERE t.moneda.id = :idMoneda")
    List<TipoCuenta> findByIdMoneda(@Param("idMoneda") String idMoneda);
    
    @Query("SELECT t FROM TipoCuenta t WHERE t.tipocliente = :tipoCliente")
    List<TipoCuenta> findByTipoCliente(@Param("tipoCliente") String tipoCliente);
}


