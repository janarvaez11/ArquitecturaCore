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
    
    @Query("SELECT DISTINCT t FROM TasaInteres t JOIN TipoCuenta tc ON t.IdTasaInteres = tc.tasaInteres.IdTasaInteres WHERE tc.IdTipoCuenta = :idTipoCuenta")
    List<TasaInteres> findByTipoCuentaId(@Param("idTipoCuenta") Integer idTipoCuenta);
    
    @Query("SELECT t FROM TasaInteres t JOIN TasaSaldo ts ON t.IdTasaInteres = ts.tasaInteres.IdTasaInteres WHERE ts.SaldoMinimo >= :saldoMinimo AND ts.SaldoMaximo <= :saldoMaximo")
    List<TasaInteres> findByRangoSaldo(@Param("saldoMinimo") Double saldoMinimo, @Param("saldoMaximo") Double saldoMaximo);
    
    @Query("SELECT t FROM TasaInteres t WHERE t.Estado = :estado")
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<TasaInteres> findByEstado(@Param("estado") String estado);
} 