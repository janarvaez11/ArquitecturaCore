package com.banquito.core.aplicacion.prestamos.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.prestamos.modelo.TipoPrestamo;

@Repository
public interface TipoPrestamoRepositorio extends JpaRepository<TipoPrestamo, Integer> {
    boolean existsByNombre(String nombre);
    
    // Buscar por estado (ACTIVO/INACTIVO)
    List<TipoPrestamo> findByEstado(String estado);
    
    // Buscar por tipo de cliente
    List<TipoPrestamo> findByTipoCliente(String tipoCliente);

    List<TipoPrestamo> findByMoneda_Id(String monedaId);

    // Buscar por nombre exacto
    TipoPrestamo findByNombre(String nombre);

    // Buscar con filtros múltiples
    @Query("SELECT tp FROM TipoPrestamo tp WHERE " +
           "(:nombre IS NULL OR tp.nombre LIKE %:nombre%) AND " +
           "(:monedaId IS NULL OR tp.moneda.id = :monedaId) AND " +
           "(:tipoCliente IS NULL OR tp.tipoCliente = :tipoCliente)")
    List<TipoPrestamo> findByFiltros(
        @Param("nombre") String nombre,
        @Param("monedaId") String monedaId,
        @Param("tipoCliente") String tipoCliente
    );
}
