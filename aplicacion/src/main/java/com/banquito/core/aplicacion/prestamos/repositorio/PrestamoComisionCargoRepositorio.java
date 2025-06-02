package com.banquito.core.aplicacion.prestamos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargo;
import com.banquito.core.aplicacion.prestamos.modelo.PrestamoComisionCargoId;

import java.util.List;

@Repository
public interface PrestamoComisionCargoRepositorio extends JpaRepository<PrestamoComisionCargo, PrestamoComisionCargoId> {
    List<PrestamoComisionCargo> findById_IdPrestamo(Integer idPrestamo);
} 