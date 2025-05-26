package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.ComisionCargo;

@Repository
public interface ComisionCargoRepositorio extends JpaRepository<ComisionCargo, Integer> {
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.servicioAsociado.tipoCuenta.idTipoCuenta = :idTipoCuenta")
    List<ComisionCargo> findByTipoCuentaId(@Param("idTipoCuenta") Integer idTipoCuenta);
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.tipoComision = :tipoComision")
    List<ComisionCargo> findByTipoComision(@Param("tipoComision") String tipoComision);
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.frecuencia = :frecuencia")
    List<ComisionCargo> findByFrecuencia(@Param("frecuencia") String frecuencia);
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.baseCalculo = :baseCalculo")
    List<ComisionCargo> findByBaseCalculo(@Param("baseCalculo") String baseCalculo);
}
