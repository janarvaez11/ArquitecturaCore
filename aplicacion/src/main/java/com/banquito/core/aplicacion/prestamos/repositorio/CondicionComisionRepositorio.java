package com.banquito.core.aplicacion.prestamos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.prestamos.modelo.CondicionComision;
import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;

@Repository
public interface CondicionComisionRepositorio extends JpaRepository<CondicionComision, Integer> {
    List<CondicionComision> findByTipoCondicion(String tipoCondicion);
    List<CondicionComision> findByComisionPrestamo(ComisionPrestamo comisionPrestamo);
}