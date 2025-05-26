package com.banquito.core.aplicacion.cuentas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.ServicioAsociado;

@Repository
public interface ServicioAsociadoRepositorio extends JpaRepository<ServicioAsociado, Integer> {
    
    @Query("SELECT s FROM ServicioAsociado s WHERE s.nombre LIKE %:nombre%")
    List<ServicioAsociado> findByNombreContaining(@Param("nombre") String nombre);
    
    List<ServicioAsociado> findByEstado(String estado);
    
    @Query("SELECT s FROM ServicioAsociado s JOIN ServicioTipoCuenta stc ON s.idServicio = stc.servicioAsociado.idServicio WHERE stc.cuenta.tipoCuenta.idTipoCuenta = :idTipoCuenta")
    List<ServicioAsociado> findByTipoCuentaId(@Param("idTipoCuenta") Integer idTipoCuenta);
}
