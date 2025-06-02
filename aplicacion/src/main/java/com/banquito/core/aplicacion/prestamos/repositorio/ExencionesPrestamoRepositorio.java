package com.banquito.core.aplicacion.prestamos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.prestamos.modelo.ComisionPrestamo;
import com.banquito.core.aplicacion.prestamos.modelo.ExencionesPrestamo;

@Repository
public interface ExencionesPrestamoRepositorio extends JpaRepository<ExencionesPrestamo, Integer> {
    boolean existsByIdComisionPrestamoAndTipoExencion(ComisionPrestamo idComisionPrestamo, String tipoExencion);
    List<ExencionesPrestamo> findByIdComisionPrestamo(ComisionPrestamo idComisionPrestamo);
}
