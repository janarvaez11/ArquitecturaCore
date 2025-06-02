package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.TasaInteres;

@Repository
public interface TasaInteresRepositorio extends JpaRepository<TasaInteres, Integer> {
    List<TasaInteres> findByTipoCuenta_IdTipoCuenta(Integer idTipoCuenta);
    List<TasaInteres> findByValorMinimoBetweenOrValorMaximoBetween(
        Double valorMinimo1, Double valorMinimo2, 
        Double valorMaximo1, Double valorMaximo2);
    List<TasaInteres> findByEstado(String estado);
}