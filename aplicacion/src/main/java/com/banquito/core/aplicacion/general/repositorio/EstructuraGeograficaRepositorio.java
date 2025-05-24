package com.banquito.core.aplicacion.general.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.modelo.EstructuraGeograficaId;


public interface EstructuraGeograficaRepositorio extends JpaRepository<EstructuraGeografica, EstructuraGeograficaId> {
     @Query("SELECT COUNT(e) > 0 FROM EstructuraGeografica e WHERE e.id.paisId = :paisId AND e.id.codigoNivel = :nivel AND e.nombre = :nombre")
    boolean existsByPaisIdAndCodigoNivelAndNombre(@Param("paisId") String paisId, 
                                                  @Param("nivel") Integer nivel, 
                                                  @Param("nombre") String nombre);
}
