package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.TasaInteres;

import jakarta.persistence.QueryHint;

@Repository
public interface TasaInteresRepositorio extends JpaRepository<TasaInteres, Integer> {
    
    @Query("SELECT DISTINCT t FROM TasaInteres t JOIN TipoCuenta tc ON t.idTasaInteres = tc.tasaInteres.idTasaInteres WHERE tc.idTipoCuenta = :idTipoCuenta")
    List<TasaInteres> findByTipoCuentaId(@Param("idTipoCuenta") Integer idTipoCuenta);
    
    @Query("SELECT t FROM TasaInteres t JOIN TasaSaldo ts ON t.idTasaInteres = ts.tasaInteres.idTasaInteres WHERE ts.saldoMinimo >= :saldoMinimo AND ts.saldoMaximo <= :saldoMaximo")
    List<TasaInteres> findByRangoSaldo(@Param("saldoMinimo") Double saldoMinimo, @Param("saldoMaximo") Double saldoMaximo);
    
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<TasaInteres> findByEstado(String estado);
} 