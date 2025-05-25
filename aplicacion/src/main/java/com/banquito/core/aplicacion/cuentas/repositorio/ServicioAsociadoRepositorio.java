package com.banquito.core.aplicacion.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.cuentas.modelo.ServicioAsociado;

public interface ServicioAsociadoRepositorio extends JpaRepository<ServicioAsociado, Integer> {
    // Aquí puedes agregar métodos personalizados de búsqueda si los necesitas
}
