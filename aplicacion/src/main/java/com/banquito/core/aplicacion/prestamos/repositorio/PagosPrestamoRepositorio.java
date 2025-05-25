package com.banquito.core.aplicacion.prestamos.repositorio;

import com.banquito.core.aplicacion.prestamos.modelo.PagosPrestamos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagosPrestamoRepositorio extends JpaRepository<PagosPrestamos, Integer> {
}
