package com.banquito.core.aplicacion.transacciones.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.aplicacion.transacciones.modelo.TipoTransaccion;

public interface TipoTransaccionRepositorio extends JpaRepository<TipoTransaccion, Integer> {

}
