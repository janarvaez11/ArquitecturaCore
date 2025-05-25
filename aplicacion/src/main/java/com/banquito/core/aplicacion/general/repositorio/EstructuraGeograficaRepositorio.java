package com.banquito.core.aplicacion.general.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.general.modelo.EstructuraGeografica;
import com.banquito.core.aplicacion.general.modelo.EstructuraGeograficaId;

public interface EstructuraGeograficaRepositorio extends JpaRepository<EstructuraGeografica, EstructuraGeograficaId> {
    List<EstructuraGeografica> findByPaisId(String paisId);

    List<EstructuraGeografica> findByIdCodigoNivel(Integer codigoNivel);

    List<EstructuraGeografica> findByPaisIdAndIdCodigoNivel(String paisId, Integer codigoNivel);

}
