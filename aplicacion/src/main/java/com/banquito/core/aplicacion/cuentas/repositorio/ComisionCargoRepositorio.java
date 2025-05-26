package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.ComisionCargo;

@Repository
public interface ComisionCargoRepositorio extends JpaRepository<ComisionCargo, Integer> {
    
    @Query("SELECT DISTINCT cc FROM ComisionCargo cc " +
           "JOIN CuentaComisionCargo ccc ON cc.IdComisionCargo = ccc.id.idComisionCargo " +
           "JOIN Cuenta c ON ccc.id.idCuenta = c.IdCuenta " +
           "WHERE c.tipoCuenta.IdTipoCuenta = :idTipoCuenta")
    List<ComisionCargo> findByTipoCuentaId(@Param("idTipoCuenta") Integer idTipoCuenta);
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.TipoComision = :tipoComision")
    List<ComisionCargo> findByTipoComision(@Param("tipoComision") String tipoComision);
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.Frecuencia = :frecuencia")
    List<ComisionCargo> findByFrecuencia(@Param("frecuencia") String frecuencia);
    
    @Query("SELECT c FROM ComisionCargo c WHERE c.BaseCalculo = :baseCalculo")
    List<ComisionCargo> findByBaseCalculo(@Param("baseCalculo") String baseCalculo);
}
