package com.banquito.core.aplicacion.general.repositorio;

import com.banquito.core.aplicacion.general.modelo.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeriadoRepositorio extends JpaRepository<Feriado, Integer> {
    List<Feriado> findByPaisId(String paisId);
    List<Feriado> findByLocacionId(Integer locacionId);
}
