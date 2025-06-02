package com.banquito.core.aplicacion.clientes.repositorio;

import com.banquito.core.aplicacion.clientes.modelo.Accionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;


public interface AccionistaRepositorio extends JpaRepository<Accionista, Integer> {
    @Query("SELECT COALESCE(SUM(a.participacion), 0) FROM Accionista a WHERE a.empresa.idEmpresa = :idEmpresa AND (:excluirId IS NULL OR a.idAccionista <> :excluirId)")
    BigDecimal sumaParticipacionPorEmpresa(@Param("idEmpresa") Integer idEmpresa, @Param("excluirId") Integer excluirId);
}
