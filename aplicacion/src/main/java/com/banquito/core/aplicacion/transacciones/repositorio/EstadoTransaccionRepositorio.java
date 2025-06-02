package com.banquito.core.aplicacion.transacciones.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banquito.core.aplicacion.transacciones.modelo.EstadoTransaccion;

public interface EstadoTransaccionRepositorio extends JpaRepository<EstadoTransaccion, String> {
    
    List<EstadoTransaccion> findByEstadoIdIn(List<String> estadoIds);
    
    List<EstadoTransaccion> findByNombreIgnoreCase(String nombre);
    
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaccion t WHERE t.estadoId = :estadoId")
    boolean existsTransaccionesByEstadoId(@Param("estadoId") String estadoId);
}
