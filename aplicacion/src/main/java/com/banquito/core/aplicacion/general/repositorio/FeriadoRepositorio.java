package com.banquito.core.aplicacion.general.repositorio;

import com.banquito.core.aplicacion.general.modelo.Feriado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeriadoRepositorio extends JpaRepository<Feriado, Integer> {
}
