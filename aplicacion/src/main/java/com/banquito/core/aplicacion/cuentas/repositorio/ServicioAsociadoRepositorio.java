package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.ServicioAsociado;

@Repository
public interface ServicioAsociadoRepositorio extends JpaRepository<ServicioAsociado, Integer> {
    
    @Query("SELECT s FROM ServicioAsociado s WHERE s.Nombre LIKE %:nombre%")
    List<ServicioAsociado> findByNombreContaining(@Param("nombre") String nombre);
    
    @Query("SELECT s FROM ServicioAsociado s WHERE s.Estado = :estado")
    List<ServicioAsociado> findByEstado(@Param("estado") String estado);
    
    @Query("SELECT DISTINCT s FROM ServicioAsociado s " +
           "JOIN ServicioTipoCuenta stc ON s.IdServicio = stc.servicioAsociado.IdServicio " +
           "JOIN Cuenta c ON stc.cuenta.IdCuenta = c.IdCuenta " +
           "WHERE c.IdCuenta = :idCuenta")
    List<ServicioAsociado> findByCuentaId(@Param("idCuenta") Integer idCuenta);
}
