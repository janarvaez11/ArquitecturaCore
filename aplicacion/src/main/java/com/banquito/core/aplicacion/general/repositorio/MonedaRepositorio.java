package com.banquito.core.aplicacion.general.repositorio;

import com.banquito.core.aplicacion.general.modelo.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonedaRepositorio extends JpaRepository<Moneda, String> {
    List<Moneda> findByPaisId(String paisId);
    List<Moneda> findByEntidadBancariaId(Integer idEntidadBancaria);
}
