package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.aplicacion.cuentas.modelo.ComisionCargo;

@Repository
public interface ComisionCargoRepositorio extends JpaRepository<ComisionCargo, Integer> {
    // Aquí puedes agregar métodos personalizados de búsqueda si los necesitas
}
