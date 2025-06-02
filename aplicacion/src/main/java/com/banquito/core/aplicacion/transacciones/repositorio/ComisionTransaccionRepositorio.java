package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.ComisionTransaccion;

public interface ComisionTransaccionRepositorio extends JpaRepository<ComisionTransaccion, Integer> {

}
