package com.banquito.core.aplicacion.general.repositorio;

import com.banquito.core.aplicacion.general.modelo.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonedaRepositorio extends JpaRepository<Moneda, String> {
}
