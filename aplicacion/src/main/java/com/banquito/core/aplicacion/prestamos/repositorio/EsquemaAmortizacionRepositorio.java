package com.banquito.core.aplicacion.prestamos.repositorio;

import com.banquito.core.aplicacion.prestamos.modelo.EsquemasAmortizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EsquemaAmortizacionRepositorio extends JpaRepository<EsquemasAmortizacion, Integer> {
}
