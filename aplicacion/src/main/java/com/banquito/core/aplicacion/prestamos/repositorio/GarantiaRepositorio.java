package com.banquito.core.aplicacion.prestamos.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.prestamos.modelo.Garantia;

public interface GarantiaRepositorio extends JpaRepository<Garantia, Integer> {
 

    List<Garantia> findByTipoGarantia(String tipoGarantia);

}
