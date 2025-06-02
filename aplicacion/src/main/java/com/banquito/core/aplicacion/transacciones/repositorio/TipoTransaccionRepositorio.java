package com.banquito.core.aplicacion.transacciones.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banquito.core.aplicacion.transacciones.modelo.TipoTransaccion;

public interface TipoTransaccionRepositorio extends JpaRepository<TipoTransaccion, Integer> {
    
    List<TipoTransaccion> findByNombreContainingIgnoreCase(String nombre);
    
    List<TipoTransaccion> findByNombreIgnoreCase(String nombre);
    
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Transaccion t WHERE t.tipoTransaccionId = :tipoTransaccionId")
    boolean existsTransaccionesByTipoTransaccionId(@Param("tipoTransaccionId") Integer tipoTransaccionId);
}
