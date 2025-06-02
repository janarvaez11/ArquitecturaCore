package com.banquito.core.aplicacion.prestamos.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.prestamos.modelo.TipoPrestamo;

@Repository
public interface TipoPrestamoRepositorio extends JpaRepository<TipoPrestamo, Integer> {
    boolean existsByNombre(String nombre);
    
    // Buscar por estado (ACTIVO/INACTIVO)
    List<TipoPrestamo> findByEstado(String estado);
    
    // Buscar por tipo de cliente
    List<TipoPrestamo> findByTipoCliente(String tipoCliente);

    List<TipoPrestamo> findByMonedaId(Integer monedaId);
}
