package com.banquito.core.aplicacion.prestamos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banquito.core.aplicacion.prestamos.modelo.CronogramasPagos;

public interface CronogramaPagoRepositorio extends JpaRepository<CronogramasPagos, Integer> {
}
