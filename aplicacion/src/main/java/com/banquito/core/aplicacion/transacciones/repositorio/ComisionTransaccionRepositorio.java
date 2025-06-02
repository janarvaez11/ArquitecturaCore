package com.banquito.core.aplicacion.transacciones.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;

public interface ComisionTransaccionRepositorio extends JpaRepository<ComisionTransaccion, Integer> {
    
    List<ComisionTransaccion> findByTipoComisionIgnoreCase(String tipoComision);
    
    @Query("SELECT CASE WHEN COUNT(tt) > 0 THEN true ELSE false END FROM TipoTransaccion tt WHERE tt.comisionId = :comisionId")
    boolean existsTiposTransaccionByComisionId(@Param("comisionId") Integer comisionId);
}
