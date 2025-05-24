package com.banquito.core.aplicacion.general.repositorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.general.modelo.LocacionGeografica;

public interface LocacionGeograficaRepositorio extends JpaRepository<LocacionGeografica, Integer> {
    List<LocacionGeografica> findByEstructuraGeograficaIdPaisId(String paisId);

    List<LocacionGeografica> findByEstructuraGeograficaIdCodigoNivel(Integer nivel);

    List<LocacionGeografica> findByEstado(String estado);

}
